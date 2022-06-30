package dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi

class ProductInDetailViewModel(
    private val interactor: ProductInDetailInteractor
) : ViewModel() {

    private val _product = MutableLiveData<ProductInDetailUi?>()
    val product: LiveData<ProductInDetailUi?> = _product

    fun getProductById(guid: String) {
        _product.value = interactor.getProductById(guid)
    }
}