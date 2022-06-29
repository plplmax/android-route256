package dev.ozon.gitlab.plplmax.feature_products_api.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

interface ProductMapper {
    fun toUi(product: ProductData): ProductUi
    fun toData(product: ProductUi): ProductData
}