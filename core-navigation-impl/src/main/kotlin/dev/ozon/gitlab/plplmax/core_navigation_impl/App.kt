package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.google.gson.Gson
import dev.ozon.gitlab.plplmax.core_navigation_impl.di.Dependencies
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailMapper
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductMapper
import dev.ozon.gitlab.plplmax.work_manager_impl.ProductsWorkerFactory
import javax.inject.Inject

class App : Application(), Configuration.Provider {

    @Inject
    lateinit var api: ProductsApi

    @Inject
    lateinit var gson: Gson

    private lateinit var productMapper: ProductMapper

    private lateinit var productInDetailMapper: ProductInDetailMapper

    override fun onCreate() {
        super.onCreate()

        Dependencies.initAppComponent(this)

        productMapper = Dependencies.featureProductsComponent.productMapper()
        productInDetailMapper = Dependencies.featureProductDetailComponent.productInDetailMapper()

        WorkManager.initialize(this, workManagerConfiguration)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val factory = DelegatingWorkerFactory().also {
            it.addFactory(ProductsWorkerFactory(api, productMapper, productInDetailMapper, gson))
        }

        return Configuration.Builder()
            .setWorkerFactory(factory)
            .build()
    }
}