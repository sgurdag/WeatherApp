package com.sgurdag.weather_detail.di.modules

import androidx.lifecycle.ViewModel
import com.safagurdag.core.di.keys.ViewModelKey
import com.sgurdag.weather_detail.ui.WeatherDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeatherDetailFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherDetailViewModel::class)
    abstract fun weatherDetailViewModel(weatherDetailViewModel: WeatherDetailViewModel): ViewModel
}