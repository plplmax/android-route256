package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import com.google.gson.Gson
import dev.ozon.gitlab.plplmax.core_navigation_impl.di.Dependencies
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailMapper

class App : Application(), Configuration.Provider {

    private lateinit var api: ProductsApi
    private lateinit var mapper: ProductInDetailMapper
    private lateinit var gson: Gson

    override fun onCreate() {
        super.onCreate()

        Dependencies.initAppComponent(this)
        api = Dependencies.appComponent!!.productsApi()
        mapper = Dependencies.featureProductDetailComponent.productInDetailMapper()
        gson = Dependencies.appComponent!!.gson()

        WorkManager.initialize(this, workManagerConfiguration)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val factory = DelegatingWorkerFactory().also {
            it.addFactory(ProductsWorkerFactory(api, mapper, gson))
        }

        return Configuration.Builder()
            .setWorkerFactory(factory)
            .build()
    }
}