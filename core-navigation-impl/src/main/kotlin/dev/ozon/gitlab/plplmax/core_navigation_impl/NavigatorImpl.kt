package dev.ozon.gitlab.plplmax.core_navigation_impl

import androidx.navigation.NavController
import dev.ozon.gitlab.plplmax.core_navigation_api.Navigator
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation.ProductInDetailFragment
import javax.inject.Inject

internal class NavigatorImpl @Inject constructor() : Navigator {
    override fun openProductInDetailScreen(navController: NavController, guid: String) {
        navController.navigate(
            R.id.action_productsFragment_to_productInDetailFragment,
            ProductInDetailFragment.newBundle(guid)
        )
    }
}