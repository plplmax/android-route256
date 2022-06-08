package dev.ozon.gitlab.plplmax.homework2.presentation.products.productInDetail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dev.ozon.gitlab.plplmax.homework2.R
import dev.ozon.gitlab.plplmax.homework2.di.ServiceLocator
import dev.ozon.gitlab.plplmax.homework2.presentation.core.load
import dev.ozon.gitlab.plplmax.homework2.presentation.core.viewModelCreator

class ProductInDetailFragment : Fragment(R.layout.pdp_fragment) {

    private val vm: ProductInDetailViewModel by viewModelCreator {
        ProductInDetailViewModel(ServiceLocator.productsInteractor)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productIV: ImageView = view.findViewById(R.id.productIV)
        val nameTV: TextView = view.findViewById(R.id.nameTV)
        val priceTV: TextView = view.findViewById(R.id.priceTV)
        val ratingView: RatingBar = view.findViewById(R.id.ratingView)

        vm.product.observe(viewLifecycleOwner) {
            if (it.images.isNotEmpty()) {
                productIV.load(it.images[0])
            }

            nameTV.text = it.name
            priceTV.text = it.price
            ratingView.rating = it.rating.toFloat()
        }

        vm.getProductById(arguments?.getString(GUID_KEY)!!)

        arguments?.let { bundle ->
            bundle.getString(GUID_KEY)?.let {
                vm.getProductById(it)
            }
        }
    }

    interface ToProductDetailNavigationListener {
        fun navigateToDetailFragment(productGuid: String)
    }

    companion object {
        private const val GUID_KEY = "guid_key"

        @JvmStatic
        fun newInstance(guid: String): ProductInDetailFragment {
            return ProductInDetailFragment().apply {
                arguments = bundleOf(GUID_KEY to guid)
            }
        }
    }
}
