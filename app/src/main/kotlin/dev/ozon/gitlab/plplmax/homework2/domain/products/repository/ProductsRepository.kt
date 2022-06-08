package dev.ozon.gitlab.plplmax.homework2.domain.products.repository

import dev.ozon.gitlab.plplmax.homework2.data.products.ProductData
import dev.ozon.gitlab.plplmax.homework2.data.products.productsData
import dev.ozon.gitlab.plplmax.homework2.data.products.productsInDetailData
import dev.ozon.gitlab.plplmax.homework2.domain.products.mapper.toUi
import dev.ozon.gitlab.plplmax.homework2.presentation.products.ProductUi
import dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail.ProductInDetailUi

interface ProductsRepository {
    fun getProducts(): List<ProductUi>
    fun getProductById(guid: String): ProductInDetailUi?

    class Mock : ProductsRepository {
        override fun getProducts(): List<ProductUi> = productsData.map(ProductData::toUi)

        override fun getProductById(guid: String): ProductInDetailUi? { // TODO: Remove nullability in the next homework
            return productsInDetailData.find { it.guid == guid }?.toUi()
        }
    }
}