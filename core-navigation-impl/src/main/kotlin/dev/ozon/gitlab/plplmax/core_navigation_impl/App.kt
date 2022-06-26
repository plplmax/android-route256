package dev.ozon.gitlab.plplmax.core_navigation_impl

import android.app.Application
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import dev.ozon.gitlab.plplmax.core_navigation_impl.di.Dependencies
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi

class App : Application(), Configuration.Provider {

    private lateinit var api: ProductsApi

    override fun onCreate() {
        super.onCreate()

        Dependencies.initAppComponent(this)
        api = Dependencies.appComponent!!.productsApi()

        WorkManager.initialize(this, workManagerConfiguration)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        val factory = DelegatingWorkerFactory().also {
            it.addFactory(ProductsWorkerFactory(api))
        }

        return Configuration.Builder()
            .setWorkerFactory(factory)
            .build()
    }
}