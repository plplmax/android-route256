package dev.ozon.gitlab.plplmax.homework2.presentation.products

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import dev.ozon.gitlab.plplmax.homework2.R
import dev.ozon.gitlab.plplmax.homework2.di.ServiceLocator
import dev.ozon.gitlab.plplmax.homework2.presentation.core.viewModelCreator
import dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail.ProductInDetailFragment

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val vm: ProductsViewModel by viewModelCreator {
        ProductsViewModel(ServiceLocator.productsInteractor)
    }

    private var listener: ProductInDetailFragment.ToProductDetailNavigationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ProductInDetailFragment.ToProductDetailNavigationListener) {
            listener = context
        } else {
            throw IllegalArgumentException(
                "${context::class.java.simpleName} must implements ToProductDetailNavigationListener"
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view.findViewById<RecyclerView>(R.id.products_recycler)) {
            adapter = ProductsAdapter { listener?.navigateToDetailFragment(it) }
            vm.productLD.observe(viewLifecycleOwner, (adapter as ProductsAdapter)::submitList)
        }
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }
}