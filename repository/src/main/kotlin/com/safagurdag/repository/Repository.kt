package com.safagurdag.repository

import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.safagurdag.repository.interactors.base.Result

abstract class Repository {

    //region Abstractions

    internal abstract suspend fun getWeatherDetails(lat:String,long:String):Result<WeatherDetailEntity>

    //endregion
}