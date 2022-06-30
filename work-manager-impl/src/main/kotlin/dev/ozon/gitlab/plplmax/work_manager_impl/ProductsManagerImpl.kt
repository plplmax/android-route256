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

    private val productsTypeToken = object : TypeToken<List<ProductUi>>() {}.type
    private val productsInDetailTypeToken = object : TypeToken<List<ProductInDetailUi>>() {}.type

    private var lastUpdateTimestamp = System.currentTimeMillis()

    override fun refreshAllProducts() {
        runWorkers()
    }

    override fun refreshAllProductsWithDelay() {
        runWorkersWithDelay()
    }

    private fun runWorkers() {
        val productsRequest = OneTimeWorkRequest.from(ProductsWorker::class.java)
        val productsInDetailRequest = OneTimeWorkRequest.from(ProductsInDetailWorker::class.java)

        workManager.beginUniqueWork(WORK_NAME, ExistingWorkPolicy.KEEP, productsRequest)
            .then(productsInDetailRequest)
            .enqueue()
    }

    private fun runWorkersWithDelay() {
        val internetConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val delay = computeDelayInMillis()

        val productsRequest =
            OneTimeWorkRequestBuilder<ProductsWorker>()
                .setConstraints(internetConstraint)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build()
        val productsInDetailRequest =
            OneTimeWorkRequestBuilder<ProductsInDetailWorker>()
                .setConstraints(internetConstraint)
                .build()

        workManager.beginUniqueWork(WORK_NAME, ExistingWorkPolicy.KEEP, productsRequest)
            .then(productsInDetailRequest)
            .enqueue()
    }

    private fun computeDelayInMillis(): Long {
        val currentTimestamp = System.currentTimeMillis()
        val passedTime = currentTimestamp - lastUpdateTimestamp

        return if (passedTime >= fiveMinutesInMillis) {
            0
        } else {
            fiveMinutesInMillis - passedTime
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
                workInfoList?.find {
                    it?.tags?.contains(ProductsInDetailWorker::class.qualifiedName) ?: false
                }
                    ?.let {
                        handleWorkInfo(
                            it,
                            onProductsSuccess,
                            onProductsInDetailSuccess,
                            productsInCache
                        )
                    }
            }
    }

    private fun handleWorkInfo(
        workInfo: WorkInfo,
        onProductsSuccess: (List<ProductUi>) -> Unit,
        onProductsInDetailSuccess: (List<ProductInDetailUi>) -> Unit,
        productsInCache: () -> List<ProductUi>
    ) {
        with(workInfo) {
            if (state == WorkInfo.State.SUCCEEDED) handleWorkSuccess(
                workInfo,
                onProductsSuccess,
                onProductsInDetailSuccess
            )
            else if (state == WorkInfo.State.FAILED) handleWorkFailure(
                onProductsSuccess,
                productsInCache
            )
        }
    }

    private fun handleWorkSuccess(
        workInfo: WorkInfo,
        onProductsSuccess: (List<ProductUi>) -> Unit,
        onProductsInDetailSuccess: (List<ProductInDetailUi>) -> Unit
    ) {
        val (products, productsInDetail) = getOutputData(workInfo)

        onProductsSuccess(products)
        onProductsInDetailSuccess(productsInDetail)

        lastUpdateTimestamp = System.currentTimeMillis()

        refreshState.value = Result.success(Unit)
    }

    private fun getOutputData(workInfo: WorkInfo): Pair<List<ProductUi>, List<ProductInDetailUi>> {
        val productsJson = workInfo.outputData.getString(ProductsWorker.PRODUCTS_KEY)
        val productsInDetailJson =
            workInfo.outputData.getString(ProductsInDetailWorker.PRODUCTS_IN_DETAIL_KEY)

        val products = gson.fromJson<List<ProductUi>>(productsJson, productsTypeToken)
        val productsInDetail = gson.fromJson<List<ProductInDetailUi>>(
            productsInDetailJson,
            productsInDetailTypeToken
        )

        return Pair(products, productsInDetail)
    }

    private fun handleWorkFailure(
        onProductsSuccess: (List<ProductUi>) -> Unit,
        productsInCache: () -> List<ProductUi>
    ) {
        with(productsInCache()) {
            refreshState.value = if (isEmpty()) {
                Result.failure(Throwable())
            } else {
                onProductsSuccess(this)
                Result.success(Unit)
            }
        }
    }

    private companion object {
        const val WORK_NAME = "RetrofitWorker"
        const val fiveMinutesInMillis = 300_000L
    }
}