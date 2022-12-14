package dev.ozon.gitlab.plplmax.core_navigation_impl.di

import dev.ozon.gitlab.plplmax.core_navigation_impl.App
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.di.DaggerFeatureProductDetailComponent
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.di.FeatureProductDetailComponent
import dev.ozon.gitlab.plplmax.feature_products_impl.di.DaggerFeatureProductsComponent
import dev.ozon.gitlab.plplmax.feature_products_impl.di.FeatureProductsComponent
import dev.ozon.gitlab.plplmax.feature_profile_impl.di.DaggerFeatureProfileComponent
import dev.ozon.gitlab.plplmax.feature_profile_impl.di.FeatureProfileComponent

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

    internal val featureProfileComponent: FeatureProfileComponent by lazy {
        DaggerFeatureProfileComponent.builder()
            .appComponentDependencies(appComponent)
            .build()
    }

    internal fun initAppComponent(app: App) {
        if (appComponent == null) appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context = app))
            .build()
            .also { it.inject(app) }
    }
}