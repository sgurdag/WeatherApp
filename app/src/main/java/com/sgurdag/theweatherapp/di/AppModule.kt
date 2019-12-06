package com.sgurdag.theweatherapp.di

import com.sgurdag.theweatherapp.MainActivity
import com.sgurdag.weather_detail.di.WeatherDetailContributor
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @ContributesAndroidInjector(
        modules = [WeatherDetailContributor::class]
    )
    abstract fun mainActivity(): MainActivity
}