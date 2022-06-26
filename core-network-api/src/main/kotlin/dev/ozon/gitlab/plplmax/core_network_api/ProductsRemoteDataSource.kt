package dev.ozon.gitlab.plplmax.core_network_api

interface ProductsRemoteDataSource {
    fun getProducts(): List<ProductData>
    fun getProductById(guid: String): ProductInDetailData?
}