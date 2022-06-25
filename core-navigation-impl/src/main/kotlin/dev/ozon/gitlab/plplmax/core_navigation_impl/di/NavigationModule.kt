package dev.ozon.gitlab.plplmax.core_navigation_impl.di

import dagger.Binds
import dagger.Module
import dev.ozon.gitlab.plplmax.core_navigation_api.Navigator
import dev.ozon.gitlab.plplmax.core_navigation_impl.NavigatorImpl
import dev.ozon.gitlab.plplmax.core_utils.AppScope

@Module
internal interface NavigationModule {

    @Binds
    @AppScope
    fun provideNavigator(navigator: NavigatorImpl): Navigator
}