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
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductsManagerImpl @Inject constructor(
    private val workManager: WorkManager,
    private val gson: Gson
) : ProductsManager {

    private val refreshState = MutableLiveData<Result<Unit>>()

    private val _products = BehaviorSubject.create<List<ProductUi>>()
    private val _productsInDetail = BehaviorSubject.create<List<ProductInDetailUi>>()

    private val productsTypeToken = object : TypeToken<List<ProductUi>>() {}
    private val productsInDetailTypeToken = object : TypeToken<List<ProductInDetailUi>>() {}

    private val internetConstraint = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    private var lastUpdateTimestamp = 0L

    override fun refreshAllProducts() {
        runWorkers()
    }

    override fun refreshAllProductsWithDelay() {
        runWorkersWithDelay()
    }

    override fun stopAllRefreshes() {
        workManager.cancelUniqueWork(WORK_NAME)
    }

    override fun productsObservable(): Observable<List<ProductUi>> {
        return _products
    }

    override fun productsInDetailObservable(): Observable<List<ProductInDetailUi>> {
        return _productsInDetail
    }

    private fun runWorkers() {
        val productsRequest = formRequest<ProductsWorker>()
        val productsInDetailRequest = formRequest<ProductsInDetailWorker>()

        beginWork(productsRequest, productsInDetailRequest)
    }

    private fun runWorkersWithDelay() {
        val delay = computeDelayInMillis()

        val productsRequest = formRequest<ProductsWorker>(internetConstraint, delay)
        val productsInDetailRequest = formRequest<ProductsInDetailWorker>(internetConstraint)

        beginWork(productsRequest, productsInDetailRequest)
    }

    private inline fun <reified T : ListenableWorker> formRequest(
        constraints: Constraints? = null,
        initialDelayInMillis: Long = 0
    ): OneTimeWorkRequest = OneTimeWorkRequestBuilder<T>().apply {
        constraints?.let { setConstraints(it) }
    }
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
        productsInCache: () -> List<ProductUi>
    ) {
        refreshState.observe(viewLifecycleOwner, observer)
        observeWorkInfo(
            viewLifecycleOwner,
            productsInCache
        )
    }

    private fun observeWorkInfo(
        viewLifecycleOwner: LifecycleOwner,
        productsInCache: () -> List<ProductUi>
    ) {
        workManager.getWorkInfosForUniqueWorkLiveData(WORK_NAME)
            .observe(viewLifecycleOwner) { workInfoList ->
                val workInfo = workInfoList?.find {
                    it?.tags?.contains(ProductsInDetailWorker::class.qualifiedName) ?: false
                }

                workInfo?.run {
                    if (state == WorkInfo.State.SUCCEEDED) {
                        handleWorkSuccess(this)
                    } else if (state == WorkInfo.State.FAILED) {
                        handleWorkFailure(productsInCache)
                    }
                }
            }
    }

    private fun handleWorkSuccess(workInfo: WorkInfo) {
        val products = getOutputData(workInfo, ProductsWorker.PRODUCTS_KEY, productsTypeToken)
        val productsInDetail = getOutputData(
            workInfo,
            ProductsInDetailWorker.PRODUCTS_IN_DETAIL_KEY,
            productsInDetailTypeToken
        )

        _products.onNext(products)
        _productsInDetail.onNext(productsInDetail)

        lastUpdateTimestamp = System.currentTimeMillis()

        refreshState.value = Result.success(Unit)
    }

    private fun <T> getOutputData(workInfo: WorkInfo, key: String, typeToken: TypeToken<T>): T {
        val json = workInfo.outputData.getString(key)

        val dataType = typeToken.type

        return gson.fromJson(json, dataType)
    }

    private fun handleWorkFailure(productsInCache: () -> List<ProductUi>) {
        val products = productsInCache()

        refreshState.value = if (products.isEmpty()) {
            Result.failure(Throwable())
        } else {
            _products.onNext(products)
            Result.success(Unit)
        }
    }

    private companion object {
        private const val WORK_NAME = "RetrofitWorker"
        private const val FIVE_MINUTES_IN_MILLIS = 300_000L
    }
}