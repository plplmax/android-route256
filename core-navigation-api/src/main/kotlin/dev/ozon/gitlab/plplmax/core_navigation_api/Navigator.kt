package dev.ozon.gitlab.plplmax.core_navigation_api

import androidx.navigation.NavController

interface Navigator {
    fun openProductInDetailScreen(navController: NavController, guid: String)
}