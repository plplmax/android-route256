package dev.ozon.gitlab.plplmax.feature_products_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductMapper
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsRepository
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import javax.inject.Inject

internal class ProductsRepositoryImpl @Inject constructor(
    private val localSource: ProductsLocalDataSource,
    private val mapper: ProductMapper
) : ProductsRepository {

    override fun getProducts(): List<ProductUi> {
        return localSource.getProducts().map(mapper::toUi)
    }

    override fun saveProducts(products: List<ProductUi>) {
        products.map(mapper::toData).also(localSource::saveProducts)
    }
}