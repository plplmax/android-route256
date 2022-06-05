package dev.ozon.gitlab.plplmax.homework2.domain.products.interactor

import dev.ozon.gitlab.plplmax.homework2.domain.products.repository.ProductsRepository
import dev.ozon.gitlab.plplmax.homework2.presentation.products.ProductUi

interface ProductsInteractor {
    fun getProducts(): List<ProductUi>
    fun getProductById(guid: String): ProductUi?

    class Base(private val repository: ProductsRepository) : ProductsInteractor {
        override fun getProducts(): List<ProductUi> = repository.getProducts()

        override fun getProductById(guid: String): ProductUi? = repository.getProductById(guid)
    }
}