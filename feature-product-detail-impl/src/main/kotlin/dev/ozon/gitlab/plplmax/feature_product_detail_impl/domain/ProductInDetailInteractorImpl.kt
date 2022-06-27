package dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain

import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailRepository
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import javax.inject.Inject

internal class ProductInDetailInteractorImpl @Inject constructor(
    private val repository: ProductInDetailRepository
) : ProductInDetailInteractor {
    override fun getProductById(guid: String): ProductInDetailUi? {
        return repository.getProductById(guid)
    }

    override fun saveProductsInDetail(products: List<ProductInDetailUi>) {
        repository.saveProductsInDetail(products)
    }
}