package com.safagurdag.repository.sources

import android.content.Context
import com.safagurdag.repository.MILISECONDS_FOR_24_HOURS
import com.safagurdag.repository.Repository
import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.safagurdag.repository.interactors.base.*
import com.safagurdag.repository.persistence.WeatherDatabase
import java.util.*
import javax.inject.Inject

internal class PersistenceSource @Inject constructor(ctx: Context) : Repository() {

    private val weatherDatabase = WeatherDatabase.getInstance(ctx)

    override suspend fun getWeatherDetails(lat: String, long: String): Result<WeatherDetailEntity> =

        weatherDatabase
            .weatherDetailDao
            .getWeatherDetail()
            .takeIf { it != null }
            ?.run {

                if (this.timestamp != null
                    && (Calendar.getInstance().timeInMillis - this.timestamp!!) < MILISECONDS_FOR_24_HOURS
                ) {
                    /**
                     * If the data is fetched in last 24 hours, it is being returned.
                     */
                    Success(this)
                } else {
                    /**
                     * if data is older than 24 hours, PersistenceDataExpired error is returned instead data.
                     */
                    Failure(PersistenceDataExpired())
                }

            } ?: Failure(PersistenceEmpty())



    internal suspend fun saveWeatherDetail(weatherDetailEntity: WeatherDetailEntity) =
        weatherDatabase
            .weatherDetailDao
            .saveWeatherDetail(weatherDetailEntity)
}