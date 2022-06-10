package dev.ozon.gitlab.plplmax.homework2.domain.products.interactor

import dev.ozon.gitlab.plplmax.homework2.domain.products.repository.ProductsRepository
import dev.ozon.gitlab.plplmax.homework2.presentation.products.ProductUi
import dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail.ProductInDetailUi

interface ProductsInteractor {
    fun getProducts(): List<ProductUi>
    fun getProductById(guid: String): ProductInDetailUi?

    class Base(private val repository: ProductsRepository) : ProductsInteractor {
        override fun getProducts(): List<ProductUi> = repository.getProducts()

        override fun getProductById(guid: String): ProductInDetailUi? =
            repository.getProductById(guid)
    }
}