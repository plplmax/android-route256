package dev.ozon.gitlab.plplmax.core_network_api

interface ProductsLocalDataSource {
    fun getProducts(): List<ProductData>
    fun getProductById(guid: String): ProductInDetailData?
    fun saveProducts(products: List<ProductData>)
    fun saveProductById(product: ProductInDetailData)
}