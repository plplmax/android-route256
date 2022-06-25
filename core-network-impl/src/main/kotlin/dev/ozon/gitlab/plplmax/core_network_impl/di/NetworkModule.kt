package dev.ozon.gitlab.plplmax.core_network_impl.di

import dagger.Binds
import dagger.Module
import dev.ozon.gitlab.plplmax.core_network_api.ProductsDataSource
import dev.ozon.gitlab.plplmax.core_network_impl.ProductsDataSourceImpl
import dev.ozon.gitlab.plplmax.core_utils.AppScope

@Module
interface NetworkModule {

    @Binds
    @AppScope
    fun provideProductsDataSource(source: ProductsDataSourceImpl): ProductsDataSource
}