package dev.ozon.gitlab.plplmax.homework2.domain.products.mapper

import dev.ozon.gitlab.plplmax.homework2.data.products.ProductData
import dev.ozon.gitlab.plplmax.homework2.data.products.ProductInDetailData
import dev.ozon.gitlab.plplmax.homework2.presentation.products.ProductUi
import dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail.ProductInDetailUi

fun ProductData.toUi(): ProductUi =
    ProductUi(guid, image, name, price, rating, isFavorite, isInCart)

fun ProductInDetailData.toUi(): ProductInDetailUi = ProductInDetailUi(
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