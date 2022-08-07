package dev.ozon.gitlab.plplmax.feature_profile_impl.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

class ProfileViewModel(
    private val productsInteractor: ProductsInteractor,
    private val productInDetailInteractor: ProductInDetailInteractor
) : ViewModel() {

    private val _productsInCart = MutableLiveData<List<ProductUi>>()
    val productsInCart: LiveData<List<ProductUi>> = Transformations.map(_productsInCart) { products ->
        products.filter { it.isInCart }
    }

//    init {
//        loadProducts()
//    }

    fun saveProducts() = productsInteractor.saveProducts(productsInCart.value!!)

    fun deleteFromCart(guid: String) {
        productInDetailInteractor.removeFromCart(guid)

        loadProducts()
    }

    fun loadProducts() {
        _productsInCart.value = productsInteractor.getProducts()
    }
}