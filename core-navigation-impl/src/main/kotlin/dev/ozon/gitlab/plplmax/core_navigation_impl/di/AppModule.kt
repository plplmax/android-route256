package dev.ozon.gitlab.plplmax.core_navigation_impl.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dev.ozon.gitlab.plplmax.core_utils.AppScope

@Module
internal class AppModule(
    private val context: Context,
) {

    @Provides
    @AppScope
    fun provideContext(): Context = context

    @Provides
    @AppScope
    fun provideGson(): Gson = Gson()
}