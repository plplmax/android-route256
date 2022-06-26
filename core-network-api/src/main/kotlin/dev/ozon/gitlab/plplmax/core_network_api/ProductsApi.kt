package dev.ozon.gitlab.plplmax.core_network_api

import retrofit2.Call
import retrofit2.http.GET

interface ProductsApi {

    @GET("50afcd4b-d81e-473e-827c-1b6cae1ea1b2")
    fun loadProducts(): Call<List<ProductData>>

    @GET("8c374376-e94e-4c5f-aa30-a9eddb0d7d0a")
    fun loadProductsInDetail(): Call<List<ProductInDetailData>>
}