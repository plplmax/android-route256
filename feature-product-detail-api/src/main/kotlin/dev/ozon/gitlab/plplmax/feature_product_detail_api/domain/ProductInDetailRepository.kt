package dev.ozon.gitlab.plplmax.feature_product_detail_api.domain

import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi

interface ProductInDetailRepository {
    fun getProductById(guid: String): ProductInDetailUi?
    fun saveProductsInDetail(products: List<ProductInDetailUi>)
    fun putInCart(guid: String)
    fun removeFromCart(guid: String)
}