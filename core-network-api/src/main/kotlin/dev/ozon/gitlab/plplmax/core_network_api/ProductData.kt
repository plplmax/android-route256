package dev.ozon.gitlab.plplmax.core_network_api

data class ProductData(
    val guid: String,
    val image: String,
    val name: String,
    val price: String,
    val rating: Double,
    val isFavorite: Boolean,
    val isInCart: Boolean,
    val countViews: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductData

        if (guid != other.guid) return false
        if (image != other.image) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (rating != other.rating) return false
        if (isFavorite != other.isFavorite) return false
        if (isInCart != other.isInCart) return false

        return true
    }

    override fun hashCode(): Int {
        var result = guid.hashCode()
        result = 31 * result + image.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + isFavorite.hashCode()
        result = 31 * result + isInCart.hashCode()
        return result
    }
}
