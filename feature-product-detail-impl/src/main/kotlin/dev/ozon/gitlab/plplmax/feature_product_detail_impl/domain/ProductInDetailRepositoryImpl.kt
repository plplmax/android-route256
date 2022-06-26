package dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.core_network_api.ProductsRemoteDataSource
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailRepository
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import javax.inject.Inject

internal class ProductInDetailRepositoryImpl @Inject constructor(
    private val localSource: ProductsLocalDataSource,
    private val remoteSource: ProductsRemoteDataSource
) : ProductInDetailRepository {
    override fun getProductById(guid: String): ProductInDetailUi? { // TODO: Remove nullability in the next homework
        return localSource.getProductById(guid)?.toUi() ?: remoteSource.getProductById(guid)?.toUi()
    }

    override fun saveProductById(product: ProductInDetailUi) {
        localSource.saveProductById(product.toData())
    }
}