package dev.ozon.gitlab.plplmax.feature_profile_impl.di

import dagger.Component
import dev.ozon.gitlab.plplmax.core_navigation_api.AppComponentDependencies
import dev.ozon.gitlab.plplmax.feature_profile_impl.presentation.ProfileFragment

@Component(dependencies = [AppComponentDependencies::class])
interface FeatureProfileComponent {
    fun inject(fragment: ProfileFragment)
}