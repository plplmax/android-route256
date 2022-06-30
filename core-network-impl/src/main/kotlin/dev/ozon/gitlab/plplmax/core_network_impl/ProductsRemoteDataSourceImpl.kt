package dev.ozon.gitlab.plplmax.core_network_impl

import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsRemoteDataSource
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor() : ProductsRemoteDataSource {
    override fun getProducts(): List<ProductData> = productsData

    override fun getProductById(guid: String): ProductInDetailData? {
        return productsInDetailData.find { it.guid == guid }
    }
}