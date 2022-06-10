package dev.ozon.gitlab.plplmax.homework2.presentation.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ozon.gitlab.plplmax.homework2.domain.products.interactor.ProductsInteractor

class ProductsViewModel(private val interactor: ProductsInteractor) : ViewModel() {

    private val _productLD = MutableLiveData<List<ProductUi>>()
    val productLD: LiveData<List<ProductUi>> = _productLD

    init {
        _productLD.value = interactor.getProducts()
    }
}