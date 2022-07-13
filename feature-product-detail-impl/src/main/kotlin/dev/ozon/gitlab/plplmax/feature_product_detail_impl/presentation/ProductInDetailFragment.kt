package dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_utils.viewModelCreator
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = view.findViewById(R.id.viewPager)

        val nameTV: TextView = view.findViewById(R.id.nameTV)
        val priceTV: TextView = view.findViewById(R.id.priceTV)
        val ratingView: RatingBar = view.findViewById(R.id.ratingView)

        val inBucketButton: CustomLoadingButton = view.findViewById(R.id.inBucketButton)

        val adapter = ProductInDetailAdapter()
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        inBucketButton.setOnClickListener {
            inBucketButton.startLoading()

            lifecycleScope.launch {
                delay(1000)

                if (inBucketButton.inBucket) vm.removeFromCart()
                else vm.putInCart()

                inBucketButton.done()
            }
        }

        vm.product.observe(viewLifecycleOwner) { product ->
            product?.let {
                adapter.submitUrls(it.images)

                nameTV.text = it.name
                priceTV.text = it.price
                ratingView.rating = it.rating.toFloat()

                if (product.isInCart) inBucketButton.done(immediately = true)
            } ?: findNavController().popBackStack()
        }

        arguments?.let { bundle ->
            bundle.getString(GUID_KEY)?.let {
                vm.getProductById(it)
            }
        } ?: findNavController().popBackStack()
    }

    companion object {
        private const val GUID_KEY = "guid_key"

        @JvmStatic
        fun newBundle(guid: String) = bundleOf(GUID_KEY to guid)
    }
}
