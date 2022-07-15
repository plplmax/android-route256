package dev.ozon.gitlab.plplmax.core_network_api

interface ProductsLocalDataSource {
    fun getProducts(): List<ProductData>
    fun getProductsInDetail(): List<ProductInDetailData>
    fun getProductById(guid: String): ProductInDetailData?
    fun saveProducts(products: List<ProductData>)
    fun saveProductsInDetail(products: List<ProductInDetailData>)
    fun putInCart(guid: String)
    fun removeFromCart(guid: String)
}