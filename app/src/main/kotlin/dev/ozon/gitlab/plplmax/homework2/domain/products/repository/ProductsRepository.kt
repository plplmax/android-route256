package dev.ozon.gitlab.plplmax.homework2.domain.products.repository

import dev.ozon.gitlab.plplmax.homework2.presentation.products.ProductUi

interface ProductsRepository {
    fun getProducts(): List<ProductUi>
    fun getProductById(guid: String): ProductUi?
}