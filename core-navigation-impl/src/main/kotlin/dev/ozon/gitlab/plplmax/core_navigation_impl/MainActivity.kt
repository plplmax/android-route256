package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_impl.di.Dependencies
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation.ProductInDetailFragment
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_impl.presentation.ProductsFragment
import dev.ozon.gitlab.plplmax.feature_profile_impl.presentation.ProfileFragment

class MainActivity : AppCompatActivity(), DependenciesInjector {

    private lateinit var interactor: ProductsInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        interactor = Dependencies.featureProductsComponent.productsInteractor()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)
    }

    override fun injectProductsFragment(fragment: Fragment) {
        Dependencies.featureProductsComponent.inject(fragment as ProductsFragment)
        fragment.productInDetailInteractor =
            Dependencies.featureProductDetailComponent.productInDetailInteractor()
    }

    override fun injectProductInDetailFragment(fragment: Fragment) {
        Dependencies.featureProductDetailComponent.inject(fragment as ProductInDetailFragment)
    }

    override fun injectProfileFragment(fragment: Fragment) {
        Dependencies.featureProfileComponent.inject(fragment as ProfileFragment)
        fragment.productsInteractor = Dependencies.featureProductsComponent.productsInteractor()
        fragment.productInDetailInteractor =
            Dependencies.featureProductDetailComponent.productInDetailInteractor()
    }
}