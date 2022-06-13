package dev.ozon.gitlab.plplmax.homework2.presentation.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.ozon.gitlab.plplmax.homework2.R
import dev.ozon.gitlab.plplmax.homework2.di.ServiceLocator
import dev.ozon.gitlab.plplmax.homework2.presentation.core.viewModelCreator
import dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail.ProductInDetailFragment

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val vm: ProductsViewModel by viewModelCreator {
        ProductsViewModel(ServiceLocator.productsInteractor)
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navController = findNavController()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view.findViewById<RecyclerView>(R.id.products_recycler)) {
            adapter = ProductsAdapter {
                navController.navigate(
                    R.id.action_productsFragment_to_productInDetailFragment,
                    ProductInDetailFragment.newBundle(guid = it)
                )
            }

            vm.productLD.observe(viewLifecycleOwner, (adapter as ProductsAdapter)::submitList)
        }
    }
}