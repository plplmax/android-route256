package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

class ProductsViewModel(
    private val productsInteractor: ProductsInteractor,
    private val productInDetailInteractor: ProductInDetailInteractor,
    private val workManager: WorkManager
) : ViewModel() {

    private val _productLD = MutableLiveData<List<ProductUi>>()
    val productLD: LiveData<List<ProductUi>> = _productLD

    init {
        val (productsRequest, productsInDetailRequest) = getRequests()
        runWorkers(productsRequest, productsInDetailRequest)
    }

    private fun runWorkers(
        productsRequest: OneTimeWorkRequest,
        productsInDetailRequest: OneTimeWorkRequest
    ) {
        workManager.beginUniqueWork(WORK_NAME, ExistingWorkPolicy.KEEP, productsRequest)
            .then(productsInDetailRequest)
            .enqueue()
    }

    private fun getRequests(): Pair<OneTimeWorkRequest, OneTimeWorkRequest> {
        return Pair(
            OneTimeWorkRequest.from(ProductsWorker::class.java),
            OneTimeWorkRequest.from(ProductsInDetailWorker::class.java)
        )
    }

    fun observeWorkInfo(viewLifecycleOwner: LifecycleOwner) {
        _productLD.value?.let { return }

        workManager.getWorkInfosForUniqueWorkLiveData(WORK_NAME)
            .observe(viewLifecycleOwner) { workInfoList ->
                workInfoList?.let {
                    val worker = it.find { workInfo ->
                        workInfo?.tags?.contains(ProductsInDetailWorker::class.qualifiedName) == true
                    } ?: return@observe

                    if (worker.state == WorkInfo.State.SUCCEEDED) {
                        val productsJson = worker.outputData.getString(ProductsWorker.PRODUCTS_KEY)
                        val productsInDetailJson =
                            worker.outputData.getString(ProductsInDetailWorker.PRODUCTS_IN_DETAIL_KEY)

                        val productsTypeToken = object : TypeToken<List<ProductUi>>() {}.type
                        val productsInDetailTypeToken =
                            object : TypeToken<List<ProductInDetailUi>>() {}.type

                        with(Gson()) {
                            val products =
                                fromJson<List<ProductUi>>(productsJson, productsTypeToken)
                            val productsInDetail = fromJson<List<ProductInDetailUi>>(
                                productsInDetailJson,
                                productsInDetailTypeToken
                            )

                            productsInteractor.saveProducts(products)
                            productInDetailInteractor.saveProductsInDetail(productsInDetail)

                            _productLD.value = productsInteractor.getProducts()
                        }
                    } else if (worker.state == WorkInfo.State.FAILED) {
                        _productLD.value = productsInteractor.getProducts()
                    }
                }
            }
    }

    fun saveProducts() = productsInteractor.saveProducts(productLD.value!!)

    private companion object {
        const val WORK_NAME = "RetrofitWorker"
    }
}