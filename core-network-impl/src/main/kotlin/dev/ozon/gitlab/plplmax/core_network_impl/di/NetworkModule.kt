package dev.ozon.gitlab.plplmax.core_network_impl.di

import dagger.Binds
import dagger.Module
import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.core_network_api.ProductsRemoteDataSource
import dev.ozon.gitlab.plplmax.core_network_api.SharedPrefsProvider
import dev.ozon.gitlab.plplmax.core_network_impl.ProductsLocalDataSourceImpl
import dev.ozon.gitlab.plplmax.core_network_impl.ProductsRemoteDataSourceImpl
import dev.ozon.gitlab.plplmax.core_network_impl.SharedPrefsProviderImpl
import dev.ozon.gitlab.plplmax.core_utils.AppScope

@Module
interface NetworkModule {

    @Binds
    @AppScope
    fun provideProductsRemoteDataSource(
        source: ProductsRemoteDataSourceImpl
    ): ProductsRemoteDataSource

    @Binds
    @AppScope
    fun provideProductsLocalDataSource(
        source: ProductsLocalDataSourceImpl
    ): ProductsLocalDataSource

    @Binds
    @AppScope
    fun provideSharedPrefsProvider(provider: SharedPrefsProviderImpl): SharedPrefsProvider
}