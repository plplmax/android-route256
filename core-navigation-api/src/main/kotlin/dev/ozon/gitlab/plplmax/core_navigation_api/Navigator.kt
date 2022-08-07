package dev.ozon.gitlab.plplmax.core_navigation_api

import androidx.navigation.NavController

interface Navigator {
    fun openProductInDetailScreenFromProducts(navController: NavController, guid: String)
    fun openProductInDetailScreenFromProfile(navController: NavController, guid: String)
    fun openProfile(navController: NavController)
}