package com.safagurdag.repository.network

import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit interface for networking
 */
internal interface Api {

    //region Get

    @GET("weather")
    suspend fun getWeatherDetails(
        @Query("lat") lat: String,
        @Query("lon") long: String
    ): Response<WeatherDetailEntity>

    //endregion
}
