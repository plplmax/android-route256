package dev.ozon.gitlab.plplmax.work_manager_impl.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.ozon.gitlab.plplmax.core_utils.AppScope
import dev.ozon.gitlab.plplmax.work_manager_api.ProductsManager
import dev.ozon.gitlab.plplmax.work_manager_impl.ProductsManagerImpl

@Module
interface WorkManagerModule {

    @Binds
    @AppScope
    fun provideProductsManager(manager: ProductsManagerImpl): ProductsManager

    companion object {

        @Provides
        @AppScope
        @JvmStatic
        fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)
    }
}