package dev.ozon.gitlab.plplmax.feature_products_api.domain

import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

interface ProductsRepository {
    fun getProducts(): List<ProductUi>
}