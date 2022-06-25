package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_impl.di.Dependencies
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation.ProductInDetailFragment
import dev.ozon.gitlab.plplmax.feature_products_impl.presentation.ProductsFragment

class MainActivity : AppCompatActivity(), DependenciesInjector {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dependencies.initAppComponent()
    }

    override fun injectProductsFragment(fragment: Fragment) {
        Dependencies.featureProductsComponent.inject(fragment as ProductsFragment)
    }

    override fun injectProductInDetailFragment(fragment: Fragment) {
        Dependencies.featureProductDetailComponent.inject(fragment as ProductInDetailFragment)
    }

}