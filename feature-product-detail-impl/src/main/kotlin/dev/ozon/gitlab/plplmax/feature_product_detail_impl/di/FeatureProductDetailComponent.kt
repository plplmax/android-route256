package dev.ozon.gitlab.plplmax.feature_product_detail_impl.di

import dagger.Component
import dev.ozon.gitlab.plplmax.core_navigation_api.AppComponentDependencies
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.presentation.ProductInDetailFragment
import javax.inject.Scope

@Component(
    modules = [DomainModule::class],
    dependencies = [AppComponentDependencies::class]
)
@FeatureProductDetailScope
interface FeatureProductDetailComponent {
    fun inject(fragment: ProductInDetailFragment)
}

@Scope
internal annotation class FeatureProductDetailScope