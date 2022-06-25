package dev.ozon.gitlab.plplmax.core_navigation_impl.di

import dagger.Component
import dev.ozon.gitlab.plplmax.core_navigation_api.AppComponentDependencies
import dev.ozon.gitlab.plplmax.core_network_impl.di.NetworkModule
import dev.ozon.gitlab.plplmax.core_utils.AppScope

@Component(modules = [NavigationModule::class, NetworkModule::class])
@AppScope
internal interface AppComponent : AppComponentDependencies