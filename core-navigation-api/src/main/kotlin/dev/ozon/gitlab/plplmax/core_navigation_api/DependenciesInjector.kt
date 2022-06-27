package dev.ozon.gitlab.plplmax.core_navigation_api

import androidx.fragment.app.Fragment

interface DependenciesInjector {
    fun injectProductsFragment(fragment: Fragment)
    fun injectProductInDetailFragment(fragment: Fragment)
}