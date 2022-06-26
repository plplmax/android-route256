package dev.ozon.gitlab.plplmax.core_navigation_api

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

interface DependenciesInjector {
    fun injectMainActivity(activity: AppCompatActivity)
    fun injectProductsFragment(fragment: Fragment)
    fun injectProductInDetailFragment(fragment: Fragment)
}