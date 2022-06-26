package dev.ozon.gitlab.plplmax.feature_products_api.domain

import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

interface ProductsInteractor {
    fun getProducts(): List<ProductUi>
    fun saveProducts(products: List<ProductUi>)
}