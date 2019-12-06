package com.sgurdag.weather_detail.di

import com.sgurdag.weather_detail.di.modules.WeatherDetailFragmentModule
import com.sgurdag.weather_detail.di.scopes.WeatherDetailFragmentScope
import com.sgurdag.weather_detail.ui.WeatherDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Contributes fragments & view models in this module
 */
@Module
abstract class WeatherDetailContributor {

    @ContributesAndroidInjector(
        modules = [WeatherDetailFragmentModule::class]
    )
    @WeatherDetailFragmentScope
    abstract fun weatherDetailFragment(): WeatherDetailFragment

}