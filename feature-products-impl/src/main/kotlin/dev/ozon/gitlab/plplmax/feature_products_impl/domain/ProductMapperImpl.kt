package dev.ozon.gitlab.plplmax.feature_products_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductMapper
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import javax.inject.Inject

class ProductMapperImpl @Inject constructor() : ProductMapper {
    override fun toUi(product: ProductData): ProductUi {
        return ProductUi(
            product.guid,
            product.image,
            product.name,
            product.price,
            product.rating,
            product.isFavorite,
            product.isInCart,
            product.countViews
        )
    }

    override fun toData(product: ProductUi): ProductData {
        return ProductData(
            product.guid,
            product.image,
            product.name,
            product.price,
            product.rating,
            product.isFavorite,
            product.isInCart,
            product.countViews
        )
    }
}