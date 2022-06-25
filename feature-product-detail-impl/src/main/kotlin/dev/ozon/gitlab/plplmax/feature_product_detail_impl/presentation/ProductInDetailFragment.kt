package dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_utils.load
import dev.ozon.gitlab.plplmax.core_utils.viewModelCreator
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.R
import javax.inject.Inject

class ProductInDetailFragment : Fragment(R.layout.pdp_fragment) {

    @Inject
    lateinit var interactor: ProductInDetailInteractor

    private val vm: ProductInDetailViewModel by viewModelCreator {
        ProductInDetailViewModel(interactor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as DependenciesInjector).injectProductInDetailFragment(this)
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

        arguments?.let { bundle ->
            bundle.getString(GUID_KEY)?.let {
                vm.getProductById(it)
            }
        }
    }

    companion object {
        private const val GUID_KEY = "guid_key"

        @JvmStatic
        fun newBundle(guid: String) = bundleOf(GUID_KEY to guid)
    }
}
