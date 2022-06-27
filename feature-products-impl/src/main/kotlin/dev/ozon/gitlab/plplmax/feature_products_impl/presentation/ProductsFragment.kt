package dev.ozon.gitlab.plplmax.feature_products_impl.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dev.ozon.gitlab.plplmax.core_navigation_api.DependenciesInjector
import dev.ozon.gitlab.plplmax.core_navigation_api.Navigator
import dev.ozon.gitlab.plplmax.core_utils.viewModelCreator
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_impl.R
import javax.inject.Inject

class ProductsFragment : Fragment(R.layout.fragment_products) {

    @Inject
    lateinit var productsInteractor: ProductsInteractor

    lateinit var productInDetailInteractor: ProductInDetailInteractor

    @Inject
    lateinit var navigator: Navigator

    private val vm: ProductsViewModel by viewModelCreator {
        ProductsViewModel(productsInteractor, productInDetailInteractor)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as DependenciesInjector).injectProductsFragment(this)

        val (productsRequest, productsInDetailRequest) = getRequests()
        runWorkers(productsRequest, productsInDetailRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view.findViewById<RecyclerView>(R.id.products_recycler)) {
            adapter = ProductsAdapter { guid ->
                vm.saveProducts()
                navigator.openProductInDetailScreen(findNavController(), guid)
            }

            vm.productLD.observe(viewLifecycleOwner, (adapter as ProductsAdapter)::submitList)
        }

        vm.observeWorkInfo(requireContext(), viewLifecycleOwner)
    }

    private fun runWorkers(
        productsRequest: OneTimeWorkRequest,
        productsInDetailRequest: OneTimeWorkRequest
    ) {
        WorkManager.getInstance(requireContext())
            .beginUniqueWork("RetrofitWorker", ExistingWorkPolicy.KEEP, productsRequest)
            .then(productsInDetailRequest)
            .enqueue()
    }

    private fun getRequests(): Pair<OneTimeWorkRequest, OneTimeWorkRequest> {
        return Pair(
            OneTimeWorkRequest.from(ProductsWorker::class.java),
            OneTimeWorkRequest.from(ProductsInDetailWorker::class.java)
        )
    }

    internal companion object {
        const val WORK_NAME = "RetrofitWorker"
    }
}