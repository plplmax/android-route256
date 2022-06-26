package dev.ozon.gitlab.plplmax.core_navigation_api

import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.core_network_api.ProductsRemoteDataSource

interface AppComponentDependencies {
    fun navigator(): Navigator
    fun productsRemoteDataSource(): ProductsRemoteDataSource
    fun productsLocalDataSource(): ProductsLocalDataSource
}