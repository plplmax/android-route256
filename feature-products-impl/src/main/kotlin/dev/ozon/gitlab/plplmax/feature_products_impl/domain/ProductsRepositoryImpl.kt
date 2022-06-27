package dev.ozon.gitlab.plplmax.feature_products_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsRepository
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import javax.inject.Inject

internal class ProductsRepositoryImpl @Inject constructor(
    private val localSource: ProductsLocalDataSource,
) : ProductsRepository {

    override fun getProducts(): List<ProductUi> {
        return localSource.getProducts().map(ProductData::toUi)
    }

    override fun saveProducts(products: List<ProductUi>) {
        products.map(ProductUi::toData).also(localSource::saveProducts)
    }
}