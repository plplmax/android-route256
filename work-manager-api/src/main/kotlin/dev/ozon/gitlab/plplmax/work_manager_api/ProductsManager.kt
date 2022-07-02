package dev.ozon.gitlab.plplmax.work_manager_api

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi

interface ProductsManager {
    fun refreshAllProducts()

    fun refreshAllProductsWithDelay()

    fun stopAllRefreshes()

    fun observeState(
        viewLifecycleOwner: LifecycleOwner,
        observer: Observer<Result<Unit>>,
        onProductsSuccess: (List<ProductUi>) -> Unit,
        onProductsInDetailSuccess: (List<ProductInDetailUi>) -> Unit,
        productsInCache: () -> List<ProductUi>
    )
}