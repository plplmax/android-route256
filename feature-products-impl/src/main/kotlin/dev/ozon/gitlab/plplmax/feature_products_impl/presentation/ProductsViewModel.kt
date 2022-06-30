package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import androidx.lifecycle.*
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import dev.ozon.gitlab.plplmax.work_manager_api.ProductsManager

class ProductsViewModel(
    private val productsInteractor: ProductsInteractor,
    private val productInDetailInteractor: ProductInDetailInteractor,
    private val productsManager: ProductsManager
) : ViewModel() {

    private val _productLD = MutableLiveData<List<ProductUi>>()
    val productLD: LiveData<List<ProductUi>> = _productLD

    init {
        refreshAllProducts()
    }

    fun refreshAllProducts() {
        productsManager.refreshAllProducts()
    }

    fun refreshAllProductsWithDelay() {
        productsManager.refreshAllProductsWithDelay()
    }

    fun observeWorkInfo(
        viewLifecycleOwner: LifecycleOwner,
        productsRefreshState: Observer<Result<Unit>>
    ) {
        productsManager.observeState(
            viewLifecycleOwner,
            productsRefreshState,
            { productsList ->
                productsInteractor.saveProducts(productsList)

                _productLD.value = productsInteractor.getProducts()
            },
            { productsInDetailList ->
                productInDetailInteractor.saveProductsInDetail(productsInDetailList)
            },
            { productsInteractor.getProducts() }
        )
    }

    fun saveProducts() = productsInteractor.saveProducts(productLD.value!!)
}