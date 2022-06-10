package dev.ozon.gitlab.plplmax.homework2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail.ProductInDetailFragment

class MainActivity : AppCompatActivity(),
    ProductInDetailFragment.ToProductDetailNavigationListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController()
    }

    override fun navigateToDetailFragment(productGuid: String) {
        navController.navigate(
            R.id.action_productsFragment_to_productInDetailFragment,
            ProductInDetailFragment.newBundle(productGuid)
        )
    }

    private fun findNavController(): NavController {
        return supportFragmentManager.findFragmentById(R.id.fragmentContainer).let {
            val navHostFragment = it as NavHostFragment
            navHostFragment.navController
        }
    }
}