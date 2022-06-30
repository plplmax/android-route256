package dev.ozon.gitlab.plplmax.feature_products_impl.di

import dagger.Binds
import dagger.Module
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductMapper
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsInteractor
import dev.ozon.gitlab.plplmax.feature_products_api.domain.ProductsRepository
import dev.ozon.gitlab.plplmax.feature_products_impl.domain.ProductMapperImpl
import dev.ozon.gitlab.plplmax.feature_products_impl.domain.ProductsInteractorImpl
import dev.ozon.gitlab.plplmax.feature_products_impl.domain.ProductsRepositoryImpl

@Module
internal interface DomainModule {

    @Binds
    @FeatureProductsScope
    fun provideProductsInteractor(interactor: ProductsInteractorImpl): ProductsInteractor

    @Binds
    @FeatureProductsScope
    fun provideProductsRepository(repository: ProductsRepositoryImpl): ProductsRepository

    @Binds
    @FeatureProductsScope
    fun provideProductMapper(mapper: ProductMapperImpl): ProductMapper
}