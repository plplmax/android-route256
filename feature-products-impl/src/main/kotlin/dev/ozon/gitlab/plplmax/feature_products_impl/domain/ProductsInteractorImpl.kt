package dev.ozon.gitlab.plplmax.feature_products_impl.domain

import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsRepository
import dev.ozon.gitlab.plplmax.feature_products_api.presentation.ProductUi
import javax.inject.Inject

internal class ProductsInteractorImpl @Inject constructor(
    private val repository: ProductsRepository
) : ProductsInteractor {
    override fun getProducts(): List<ProductUi> = repository.getProducts()
}