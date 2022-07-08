package dev.ozon.gitlab.plplmax.work_manager_api

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.ozon.gitlab.plplmax.feature_product_detail_api.presentation.ProductInDetailUi
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import io.reactivex.rxjava3.core.Observable

interface ProductsManager {
    fun refreshAllProducts()

    fun refreshAllProductsWithDelay()

    fun stopAllRefreshes()

    fun productsObservable(): Observable<List<ProductUi>>

    fun productsInDetailObservable(): Observable<List<ProductInDetailUi>>

    fun observeState(
        viewLifecycleOwner: LifecycleOwner,
        observer: Observer<Result<Unit>>,
        productsInCache: () -> List<ProductUi>
    )
}