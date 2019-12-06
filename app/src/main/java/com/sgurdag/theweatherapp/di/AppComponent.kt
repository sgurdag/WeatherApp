package com.sgurdag.theweatherapp.di

import com.safagurdag.core.di.CoreComponent
import com.sgurdag.theweatherapp.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class
    ],
    dependencies = [CoreComponent::class]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory{
        fun create(component: CoreComponent):AppComponent
    }
}