package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import androidx.lifecycle.*
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import dev.ozon.gitlab.plplmax.work_manager_api.ProductsManager
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ProductsViewModel(
    private val productsInteractor: ProductsInteractor,
    private val productInDetailInteractor: ProductInDetailInteractor,
    private val productsManager: ProductsManager
) : ViewModel() {

    private val _productLD = MutableLiveData<List<ProductUi>>()
    val productLD: LiveData<List<ProductUi>> = _productLD

    private val compositeDisposable = CompositeDisposable()

    init {
        refreshAllProducts()

        val productsDisposable = productsManager.productsObservable()
            .subscribe {
                productsInteractor.saveProducts(it)
                _productLD.value = productsInteractor.getProducts()
            }

        val productsInDetailDisposable = productsManager.productsInDetailObservable()
            .subscribe(productInDetailInteractor::saveProductsInDetail)

        compositeDisposable.addAll(productsDisposable, productsInDetailDisposable)
    }

    fun refreshAllProducts() {
        productsManager.refreshAllProducts()
    }

    fun refreshAllProductsWithDelay() {
        productsManager.refreshAllProductsWithDelay()
    }

    fun stopAllRefreshes() {
        productsManager.stopAllRefreshes()
    }

    fun observeRefreshState(
        viewLifecycleOwner: LifecycleOwner,
        productsRefreshState: Observer<Result<Unit>>
    ): Unit = with(productsManager) {
        observeState(
            viewLifecycleOwner,
            observer = productsRefreshState,
        )

        observeState(viewLifecycleOwner) { refreshResult ->
            if (refreshResult.isSuccess) refreshAllProductsWithDelay()
        }

        observeWorkInfo(
            viewLifecycleOwner,
            productsInCache = { productsInteractor.getProducts() }
        )
    }

    fun saveProducts() = productsInteractor.saveProducts(productLD.value!!)

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}