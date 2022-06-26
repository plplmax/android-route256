package dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain

import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi

fun ProductInDetailData.toUi(): ProductInDetailUi =
    ProductInDetailUi(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        images,
        weight,
        count,
        availableCount,
        additionalParams
    )

internal fun ProductInDetailUi.toData(): ProductInDetailData =
    ProductInDetailData(
        guid,
        name,
        price,
        description,
        rating,
        isFavorite,
        isInCart,
        images,
        weight,
        count,
        availableCount,
        additionalParams
    )