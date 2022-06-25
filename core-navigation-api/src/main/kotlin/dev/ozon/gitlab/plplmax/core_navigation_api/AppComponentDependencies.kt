package dev.ozon.gitlab.plplmax.core_navigation_api

import dev.ozon.gitlab.plplmax.core_network_api.ProductsDataSource

interface AppComponentDependencies {
    fun navigator(): Navigator
    fun productsDataSource(): ProductsDataSource
}