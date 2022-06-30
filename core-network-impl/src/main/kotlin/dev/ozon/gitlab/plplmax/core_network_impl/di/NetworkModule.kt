package dev.ozon.gitlab.plplmax.core_network_impl.di

import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.ozon.gitlab.plplmax.core_network_api.ProductsApi
import dev.ozon.gitlab.plplmax.core_network_api.ProductsLocalDataSource
import dev.ozon.gitlab.plplmax.core_network_api.ProductsRemoteDataSource
import dev.ozon.gitlab.plplmax.core_network_api.SharedPrefsProvider
import dev.ozon.gitlab.plplmax.core_network_impl.ProductsLocalDataSourceImpl
import dev.ozon.gitlab.plplmax.core_network_impl.ProductsRemoteDataSourceImpl
import dev.ozon.gitlab.plplmax.core_network_impl.SharedPrefsProviderImpl
import dev.ozon.gitlab.plplmax.core_utils.AppScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface NetworkModule {

    companion object {
        private const val BASE_URL = "https://run.mocky.io/v3/"

        @Provides
        @AppScope
        @JvmStatic
        fun provideRetrofit(gson: Gson): ProductsApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ProductsApi::class.java)
        }
    }

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