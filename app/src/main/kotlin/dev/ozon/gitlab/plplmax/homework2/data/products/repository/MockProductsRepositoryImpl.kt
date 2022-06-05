package dev.ozon.gitlab.plplmax.homework2.data.products.repository

import dev.ozon.gitlab.plplmax.homework2.data.products.ProductData
import dev.ozon.gitlab.plplmax.homework2.data.products.toUi
import dev.ozon.gitlab.plplmax.homework2.domain.products.repository.ProductsRepository
import dev.ozon.gitlab.plplmax.homework2.presentation.products.ProductUi

class MockProductsRepositoryImpl : ProductsRepository {
    override fun getProducts(): List<ProductUi> = productsData.map(ProductData::toUi)

    override fun getProductById(guid: String): ProductUi? { // TODO: Remove nullability in the next homework
        return productsData.find { it.guid == guid }?.toUi()
    }
}