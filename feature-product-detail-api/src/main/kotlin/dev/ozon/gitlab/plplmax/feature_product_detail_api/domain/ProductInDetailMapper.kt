package dev.ozon.gitlab.plplmax.feature_product_detail_api.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi

interface ProductInDetailMapper {
    fun toUi(product: ProductInDetailData): ProductInDetailUi
    fun toData(product: ProductInDetailUi): ProductInDetailData
}