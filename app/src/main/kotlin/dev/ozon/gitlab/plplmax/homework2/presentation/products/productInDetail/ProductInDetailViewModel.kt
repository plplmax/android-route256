package dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ozon.gitlab.plplmax.homework2.domain.products.interactor.ProductsInteractor

class ProductInDetailViewModel(private val interactor: ProductsInteractor) : ViewModel() {

    private val _product = MutableLiveData<ProductInDetailUi>()
    val product: LiveData<ProductInDetailUi> = _product

    fun getProductById(guid: String) {
        _product.value = interactor.getProductById(guid)
    }
}