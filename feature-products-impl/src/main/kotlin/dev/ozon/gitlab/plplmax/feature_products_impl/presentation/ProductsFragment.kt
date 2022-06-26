package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_api.Navigator
import dev.ozon.gitlab.plplmax.core_utils.viewModelCreator
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_impl.R
import javax.inject.Inject

class ProductsFragment : Fragment(R.layout.fragment_products) {

    @Inject
    lateinit var interactor: ProductsInteractor

    @Inject
    lateinit var navigator: Navigator

    private val vm: ProductsViewModel by viewModelCreator {
        ProductsViewModel(requireActivity().applicationContext, viewLifecycleOwner, interactor)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as DependenciesInjector).injectProductsFragment(this)

        with(view.findViewById<RecyclerView>(R.id.products_recycler)) {
            adapter = ProductsAdapter { guid ->
                vm.saveProducts()
                navigator.openProductInDetailScreen(findNavController(), guid)
            }

            vm.productLD.observe(viewLifecycleOwner, (adapter as ProductsAdapter)::submitList)
        }
    }
}