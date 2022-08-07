package dev.ozon.gitlab.plplmax.core_network_impl

import com.google.gson.reflect.TypeToken
import dev.ozon.gitlab.plplmax.core_network_api.ProductData
import dev.ozon.gitlab.plplmax.core_network_api.ProductInDetailData
import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.core_network_api.SharedPrefsProvider
import javax.inject.Inject

class ProductsLocalDataSourceImpl @Inject constructor(
    private val sharedPrefsProvider: SharedPrefsProvider
) : ProductsLocalDataSource {

    private val productsTypeToken = object : TypeToken<List<ProductData>>() {}
    private val productsInDetailTypeToken = object : TypeToken<List<ProductInDetailData>>() {}

    override fun getProducts(): List<ProductData> {
        return sharedPrefsProvider.getObject(FILENAME_PRODUCTS, PRODUCT_LIST_KEY, productsTypeToken)
            ?: emptyList()
    }

    override fun getProductsInDetail(): List<ProductInDetailData> {
        return sharedPrefsProvider.getObject(
            FILENAME_PRODUCTS_IN_DETAIL,
            PRODUCT_IN_DETAIL_LIST_KEY,
            productsInDetailTypeToken
        ) ?: emptyList()
    }

    override fun getProductById(guid: String): ProductInDetailData? {
        return getProductsInDetail().find { it.guid == guid }
    }

    override fun saveProducts(products: List<ProductData>) {
        sharedPrefsProvider.saveObject(
            FILENAME_PRODUCTS,
            PRODUCT_LIST_KEY,
            products,
            productsTypeToken
        )
    }

    override fun saveProductsInDetail(products: List<ProductInDetailData>) {
        sharedPrefsProvider.saveObject(
            FILENAME_PRODUCTS_IN_DETAIL,
            PRODUCT_IN_DETAIL_LIST_KEY,
            products,
            productsInDetailTypeToken
        )
    }

    override fun putInCart(guid: String) {
        changeInCartState(guid, isInCart = true)
    }

    override fun removeFromCart(guid: String) {
        changeInCartState(guid, isInCart = false)
    }

    private fun changeInCartState(guid: String, isInCart: Boolean) {
        val products = getProducts()
        val productsInDetail = getProductsInDetail()

        val product = products.find { it.guid == guid } ?: return
        val productInDetail = productsInDetail.find { it.guid == guid } ?: return

        product.isInCart = isInCart
        productInDetail.isInCart = isInCart

        saveProducts(products)
        saveProductsInDetail(productsInDetail)
    }

    private companion object {
        const val FILENAME_PRODUCTS = "products"
        const val FILENAME_PRODUCTS_IN_DETAIL = "products_in_detail"

        const val PRODUCT_LIST_KEY = "product_list_key"
        const val PRODUCT_IN_DETAIL_LIST_KEY = "product_in_detail_list_key"
    }
}