package dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailMapper
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailRepository
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import javax.inject.Inject

internal class ProductInDetailRepositoryImpl @Inject constructor(
    private val localSource: ProductsLocalDataSource,
    private val mapper: ProductInDetailMapper
) : ProductInDetailRepository {

    override fun getProductById(guid: String): ProductInDetailUi? {
        return localSource.getProductById(guid)?.let(mapper::toUi)
    }

    override fun saveProductsInDetail(products: List<ProductInDetailUi>) {
        products.map(mapper::toData).also(localSource::saveProductsInDetail)
    }

    override fun putInCart(guid: String) {
        localSource.putInCart(guid)
    }

    override fun removeFromCart(guid: String) {
        localSource.removeFromCart(guid)
    }
}