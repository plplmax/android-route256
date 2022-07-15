package dev.ozon.gitlab.plplmax.feature_products_api.presentation

data class ProductUi(
    val guid: String,
    val image: List<String>,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    var countViews: Int = 0
)
