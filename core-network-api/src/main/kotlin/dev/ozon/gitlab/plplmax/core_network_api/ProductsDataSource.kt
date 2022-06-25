package dev.ozon.gitlab.plplmax.core_network_api

interface ProductsDataSource {
    fun getProducts(): List<ProductData>
    fun getProductById(guid: String): ProductInDetailData?
}