package dev.ozon.gitlab.plplmax.core_navigation_impl.di

import dagger.Component
import dev.ozon.gitlab.plplmax.core_navigation_api.AppComponentDependencies
import dev.ozon.gitlab.plplmax.core_navigation_impl.App
import dev.ozon.gitlab.plplmax.core_network_impl.di.NetworkModule
import dev.ozon.gitlab.plplmax.core_utils.AppScope
import dev.ozon.gitlab.plplmax.work_manager_impl.di.WorkManagerModule

@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        NetworkModule::class,
        WorkManagerModule::class
    ]
)
@AppScope
internal interface AppComponent : AppComponentDependencies {
    fun inject(app: App)
}