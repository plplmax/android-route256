package dev.ozon.gitlab.plplmax.core_network_api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ProductsApi {

    @GET("ee6876a1-8c02-45aa-bde4-b91817a8b210")
    fun loadProducts(): Single<List<ProductData>>

    @GET("d1b4763b-a5ea-471f-83bf-796da466e3d8")
    fun loadProductsInDetail(): Single<List<ProductInDetailData>>
}