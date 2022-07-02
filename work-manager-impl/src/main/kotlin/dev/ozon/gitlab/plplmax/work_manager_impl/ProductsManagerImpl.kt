package dev.ozon.gitlab.plplmax.work_manager_impl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import dev.ozon.gitlab.plplmax.work_manager_api.ProductsManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductsManagerImpl @Inject constructor(
    private val workManager: WorkManager,
    private val gson: Gson
) : ProductsManager {

    private val refreshState = MutableLiveData<Result<Unit>>()

    private val productsTypeToken = object : TypeToken<List<ProductUi>>() {}
    private val productsInDetailTypeToken = object : TypeToken<List<ProductInDetailUi>>() {}

    private val internetConstraint = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private var lastUpdateTimestamp = System.currentTimeMillis()

    override fun refreshAllProducts() {
        runWorkers()
    }

    override fun refreshAllProductsWithDelay() {
        runWorkersWithDelay()
    }

    override fun stopAllRefreshes() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    private fun runWorkers() {
        val productsRequest = OneTimeWorkRequest.from(ProductsWorker::class.java)
        val productsInDetailRequest = OneTimeWorkRequest.from(ProductsInDetailWorker::class.java)

        beginWork(productsRequest, productsInDetailRequest)
    }

    private fun runWorkersWithDelay() {
        val delay = computeDelayInMillis()

        val productsRequest = formRequest<ProductsWorker>(internetConstraint, delay)
        val productsInDetailRequest = formRequest<ProductsInDetailWorker>(internetConstraint)

        beginWork(productsRequest, productsInDetailRequest)
    }

    private inline fun <reified T : ListenableWorker> formRequest(
        constraints: Constraints,
        initialDelayInMillis: Long = 0
    ): OneTimeWorkRequest = OneTimeWorkRequestBuilder<T>().setConstraints(constraints)
        .setInitialDelay(initialDelayInMillis, TimeUnit.MILLISECONDS)
        .build()

    private fun beginWork(
        productsRequest: OneTimeWorkRequest,
        productsInDetailRequest: OneTimeWorkRequest
    ): Operation = workManager.beginUniqueWork(WORK_NAME, ExistingWorkPolicy.KEEP, productsRequest)
        .then(productsInDetailRequest)
        .enqueue()

    private fun computeDelayInMillis(): Long {
        val currentTimestamp = System.currentTimeMillis()
        val passedTime = currentTimestamp - lastUpdateTimestamp

        return if (passedTime >= FIVE_MINUTES_IN_MILLIS) {
            0
        } else {
            FIVE_MINUTES_IN_MILLIS - passedTime
        }
    }

    override fun observeState(
        viewLifecycleOwner: LifecycleOwner,
        observer: Observer<Result<Unit>>,
        onProductsSuccess: (List<ProductUi>) -> Unit,
        onProductsInDetailSuccess: (List<ProductInDetailUi>) -> Unit,
        productsInCache: () -> List<ProductUi>
    ) {
        refreshState.observe(viewLifecycleOwner, observer)
        observeWorkInfo(
            viewLifecycleOwner,
            onProductsSuccess,
            onProductsInDetailSuccess,
            productsInCache
        )
    }

    private fun observeWorkInfo(
        viewLifecycleOwner: LifecycleOwner,
        onProductsSuccess: (List<ProductUi>) -> Unit,
        onProductsInDetailSuccess: (List<ProductInDetailUi>) -> Unit,
        productsInCache: () -> List<ProductUi>
    ) {
        workManager.getWorkInfosForUniqueWorkLiveData(WORK_NAME)
            .observe(viewLifecycleOwner) { workInfoList ->
                val workInfo = workInfoList?.find {
                    it?.tags?.contains(ProductsInDetailWorker::class.qualifiedName) ?: false
                }

                workInfo?.run {
                    if (state == WorkInfo.State.SUCCEEDED) {
                        handleWorkSuccess(
                            this,
                            onProductsSuccess,
                            onProductsInDetailSuccess
                        )
                    } else if (state == WorkInfo.State.FAILED) {
                        handleWorkFailure(
                            onProductsSuccess,
                            productsInCache
                        )
                    }
                }
            }
    }

    private fun handleWorkSuccess(
        workInfo: WorkInfo,
        onProductsSuccess: (List<ProductUi>) -> Unit,
        onProductsInDetailSuccess: (List<ProductInDetailUi>) -> Unit
    ) {
        val products = getOutputData(workInfo, ProductsWorker.PRODUCTS_KEY, productsTypeToken)
        val productsInDetail = getOutputData(
            workInfo,
            ProductsInDetailWorker.PRODUCTS_IN_DETAIL_KEY,
            productsInDetailTypeToken
        )

        onProductsSuccess(products)
        onProductsInDetailSuccess(productsInDetail)

        lastUpdateTimestamp = System.currentTimeMillis()

        refreshState.value = Result.success(Unit)
    }

    private fun <T> getOutputData(workInfo: WorkInfo, key: String, typeToken: TypeToken<T>): T {
        val json = workInfo.outputData.getString(key)

        val dataType = typeToken.type

        return gson.fromJson(json, dataType)
    }

    private fun handleWorkFailure(
        onProductsSuccess: (List<ProductUi>) -> Unit,
        productsInCache: () -> List<ProductUi>
    ) {
        val products = productsInCache()

        refreshState.value = if (products.isEmpty()) {
            Result.failure(Throwable())
        } else {
            onProductsSuccess(products)
            Result.success(Unit)
        }
    }

    private companion object {
        private const val WORK_NAME = "RetrofitWorker"
        private const val FIVE_MINUTES_IN_MILLIS = 300_000L
    }
}