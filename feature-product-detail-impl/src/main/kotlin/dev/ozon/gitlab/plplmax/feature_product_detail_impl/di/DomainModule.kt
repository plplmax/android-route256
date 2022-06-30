package dev.ozon.gitlab.plplmax.feature_product_detail_impl.di

import dagger.Binds
import dagger.Module
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailInteractor
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailMapper
import dev.ozon.gitlab.plplmax.feature_product_detail_api.domain.ProductInDetailRepository
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain.ProductInDetailInteractorImpl
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain.ProductInDetailMapperImpl
import dev.ozon.gitlab.plplmax.feature_product_detail_impl.domain.ProductInDetailRepositoryImpl

@Module
internal interface DomainModule {

    @Binds
    @FeatureProductDetailScope
    fun provideProductInDetailInteractor(
        interactor: ProductInDetailInteractorImpl
    ): ProductInDetailInteractor

    @Binds
    @FeatureProductDetailScope
    fun provideProductInDetailRepository(
        repository: ProductInDetailRepositoryImpl
    ): ProductInDetailRepository

    @Binds
    @FeatureProductDetailScope
    fun provideProductInDetailMapper(mapper: ProductInDetailMapperImpl): ProductInDetailMapper
}