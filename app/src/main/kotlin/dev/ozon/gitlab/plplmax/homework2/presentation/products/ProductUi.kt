package dev.ozon.gitlab.plplmax.homework2.presentation.products

data class ProductUi(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    var countViews: Int = 0
)
