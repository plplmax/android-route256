package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_api.Navigator
import dev.ozon.gitlab.plplmax.core_utils.BaseProductsAdapter
import dev.ozon.gitlab.plplmax.core_utils.viewModelCreator
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_impl.R
import dev.ozon.gitlab.plplmax.work_manager_api.ProductsManager
import javax.inject.Inject

class ProductsFragment : Fragment(R.layout.fragment_products) {

    @Inject
    lateinit var productsInteractor: ProductsInteractor

    lateinit var productInDetailInteractor: ProductInDetailInteractor

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var productsManager: ProductsManager

    private val vm: ProductsViewModel by viewModelCreator {
        ProductsViewModel(
            productsInteractor,
            productInDetailInteractor,
            productsManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as DependenciesInjector).injectProductsFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("TEST", "onViewCreated: ")

        with(view.findViewById<RecyclerView>(R.id.products_recycler)) {
            val block: (String) -> Unit = { guid ->
//                vm.saveProducts()
                navigator.openProductInDetailScreenFromProducts(findNavController(), guid)
            }

            val firstAdapter = BaseProductsAdapter(block)
            val secondAdapter = BaseProductsAdapter(block)

            adapter = ConcatAdapter(
                HeaderAdapter("Продукты меньше 100 рублей"),
                firstAdapter,
                HeaderAdapter("Продукты 100 рублей и выше"),
                secondAdapter
            )

            vm.productsBelow100Rubles.observe(viewLifecycleOwner, firstAdapter::submitList)

            vm.productsEqOrAbove100Rubles.observe(viewLifecycleOwner, secondAdapter::submitList)

            vm.observeRefreshState(viewLifecycleOwner) { refreshResult ->
                if (refreshResult.isFailure) showError()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (vm.productLD.value != null) {
            vm.refreshAllProductsWithDelay()
        }
    }

    override fun onPause() {
        super.onPause()

        vm.stopAllRefreshes()
    }

    private fun showError() {
        Snackbar.make(
            requireView(),
            dev.ozon.gitlab.plplmax.core_resources.R.string.something_went_wrong,
            Snackbar.LENGTH_INDEFINITE
        ).setAction(dev.ozon.gitlab.plplmax.core_resources.R.string.retry) {
            vm.refreshAllProducts()
        }.show()
    }
}