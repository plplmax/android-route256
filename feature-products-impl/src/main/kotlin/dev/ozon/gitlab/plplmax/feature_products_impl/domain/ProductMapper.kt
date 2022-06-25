package dev.ozon.gitlab.plplmax.feature_products_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

internal fun ProductData.toUi(): ProductUi =
    ProductUi(
        guid,
        image,
        name,
        price,
        rating,
        isFavorite,
        isInCart
    )