package dev.ozon.gitlab.plplmax.core_network_api

interface ProductsLocalDataSource {
    fun getProducts(): List<ProductData>
    fun getProductById(guid: String): ProductInDetailData?
    fun saveProducts(products: List<ProductData>)
    fun saveProductsInDetail(products: List<ProductInDetailData>)
}