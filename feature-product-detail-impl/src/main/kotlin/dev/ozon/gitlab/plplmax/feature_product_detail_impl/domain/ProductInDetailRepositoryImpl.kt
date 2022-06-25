package dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductsDataSource
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailRepository
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import javax.inject.Inject

internal class ProductInDetailRepositoryImpl @Inject constructor(
    private val source: ProductsDataSource
) : ProductInDetailRepository {
    override fun getProductById(guid: String): ProductInDetailUi? { // TODO: Remove nullability in the next homework
        return source.getProductById(guid)?.toUi()
    }
}