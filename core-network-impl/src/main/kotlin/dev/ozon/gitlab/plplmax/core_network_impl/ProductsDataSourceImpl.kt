package dev.ozon.gitlab.plplmax.core_network_impl

import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsDataSource
import javax.inject.Inject

class ProductsDataSourceImpl @Inject constructor() : ProductsDataSource {
    override fun getProducts(): List<ProductData> = productsData

    override fun getProductById(guid: String): ProductInDetailData? { // TODO: Remove nullability in the next homework
        return productsInDetailData.find { it.guid == guid }
    }
}