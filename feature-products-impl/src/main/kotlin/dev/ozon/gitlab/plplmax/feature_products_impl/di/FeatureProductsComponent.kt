package dev.ozon.gitlab.plplmax.feature_products_impl.di

import dagger.Component
import dev.ozon.gitlab.plplmax.core_navigation_api.AppComponentDependencies
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_impl.presentation.ProductsFragment
import javax.inject.Scope

@Component(
    modules = [DomainModule::class],
    dependencies = [AppComponentDependencies::class]
)
@FeatureProductsScope
interface FeatureProductsComponent {
    fun inject(fragment: ProductsFragment)
    fun productsInteractor(): ProductsInteractor
}

@Scope
internal annotation class FeatureProductsScope