package dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailMapper
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import javax.inject.Inject

internal class ProductInDetailMapperImpl @Inject constructor() : ProductInDetailMapper {
    override fun toUi(product: ProductInDetailData): ProductInDetailUi {
        return ProductInDetailUi(
            product.guid,
            product.name,
            product.price,
            product.description,
            product.rating,
            product.isFavorite,
            product.isInCart,
            product.images,
            product.weight,
            product.count,
            product.availableCount,
            product.additionalParams
        )
    }

    override fun toData(product: ProductInDetailUi): ProductInDetailData {
        return ProductInDetailData(
            product.guid,
            product.name,
            product.price,
            product.description,
            product.rating,
            product.isFavorite,
            product.isInCart,
            product.images,
            product.weight,
            product.count,
            product.availableCount,
            product.additionalParams
        )
    }
}