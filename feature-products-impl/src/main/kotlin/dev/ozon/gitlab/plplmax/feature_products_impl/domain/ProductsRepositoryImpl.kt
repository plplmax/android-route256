package dev.ozon.gitlab.plplmax.feature_products_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsDataSource
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsRepository
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import javax.inject.Inject

internal class ProductsRepositoryImpl @Inject constructor(
    private val source: ProductsDataSource
) : ProductsRepository {
    override fun getProducts(): List<ProductUi> = source.getProducts().map(ProductData::toUi)
}