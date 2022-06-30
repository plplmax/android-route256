package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_impl.di.Dependencies
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation.ProductInDetailFragment
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_impl.presentation.ProductsFragment

class MainActivity : AppCompatActivity(), DependenciesInjector {

    private lateinit var interactor: ProductsInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        interactor = Dependencies.featureProductsComponent.productsInteractor()
    }

    override fun injectProductsFragment(fragment: Fragment) {
        Dependencies.featureProductsComponent.inject(fragment as ProductsFragment)
        fragment.productInDetailInteractor =
            Dependencies.featureProductDetailComponent.productInDetailInteractor()
    }

    override fun injectProductInDetailFragment(fragment: Fragment) {
        Dependencies.featureProductDetailComponent.inject(fragment as ProductInDetailFragment)
    }
}