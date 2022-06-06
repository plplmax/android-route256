package dev.ozon.gitlab.plplmax.homework2.data.products

import dev.ozon.gitlab.plplmax.homework2.presentation.products.ProductUi

fun ProductData.toUi(): ProductUi =
    ProductUi(guid, image, name, price, rating, isFavorite, isInCart)