package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

class ProductsViewModel(private val interactor: ProductsInteractor) : ViewModel() {

    private val _productLD = MutableLiveData<List<ProductUi>>()
    val productLD: LiveData<List<ProductUi>> = _productLD

    init {
        _productLD.value = interactor.getProducts()
    }

    fun saveProducts() = interactor.saveProducts(productLD.value!!)
}