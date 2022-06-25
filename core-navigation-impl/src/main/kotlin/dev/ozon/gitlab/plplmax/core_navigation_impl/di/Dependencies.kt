package dev.ozon.gitlab.plplmax.core_navigation_impl.di

import dev.ozon.gitlab.plplmax.feature_product_detail_impl.di.DaggerFeatureProductDetailComponent
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.di.FeatureProductDetailComponent
import dev.ozon.gitlab.plplmax.feature_products_impl.di.DaggerFeatureProductsComponent
import dev.ozon.gitlab.plplmax.feature_products_impl.di.FeatureProductsComponent

internal object Dependencies {
    private var appComponent: AppComponent? = null

    internal val featureProductsComponent: FeatureProductsComponent by lazy {
        DaggerFeatureProductsComponent.builder()
            .appComponentDependencies(appComponent)
            .build()
    }

    internal val featureProductDetailComponent: FeatureProductDetailComponent by lazy {
        DaggerFeatureProductDetailComponent.builder()
            .appComponentDependencies(appComponent)
            .build()
    }

    internal fun initAppComponent() {
        if (appComponent == null) appComponent = DaggerAppComponent.create()
    }
}