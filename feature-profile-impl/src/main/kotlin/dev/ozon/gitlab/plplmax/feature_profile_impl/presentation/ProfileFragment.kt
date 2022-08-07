package dev.ozon.gitlab.plplmax.feature_profile_impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_api.Navigator
import dev.ozon.gitlab.plplmax.core_utils.viewModelCreator
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_profile_impl.R
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var productsInteractor: ProductsInteractor

    lateinit var productInDetailInteractor: ProductInDetailInteractor

    @Inject
    lateinit var navigator: Navigator

    private lateinit var adapter: InCartProductsAdapter

    private val vm: ProfileViewModel by viewModelCreator {
        ProfileViewModel(
            productsInteractor,
            productInDetailInteractor
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as DependenciesInjector).injectProfileFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupObservation()
    }

    private fun setupRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = InCartProductsAdapter(
            { guid -> vm.deleteFromCart(guid) },
            { guid ->
                vm.saveProducts()
                navigator.openProductInDetailScreenFromProfile(findNavController(), guid)
            }
        ).also { adapter = it }
    }

    private fun setupObservation() {
        vm.productsInCart.observe(viewLifecycleOwner, adapter::submitList)
        vm.loadProducts()
    }
}