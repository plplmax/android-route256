package dev.ozon.gitlab.plplmax.core_navigation_api

import com.google.gson.Gson
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.core_network_api.ProductsRemoteDataSource

interface AppComponentDependencies {
    fun navigator(): Navigator
    fun productsRemoteDataSource(): ProductsRemoteDataSource
    fun productsLocalDataSource(): ProductsLocalDataSource
    fun productsApi(): ProductsApi
    fun gson(): Gson
}