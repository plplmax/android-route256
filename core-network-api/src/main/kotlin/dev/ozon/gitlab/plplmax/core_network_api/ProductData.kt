package dev.ozon.gitlab.plplmax.core_network_api

data class ProductData(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean
)