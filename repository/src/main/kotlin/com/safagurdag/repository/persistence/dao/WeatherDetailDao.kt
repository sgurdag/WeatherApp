package com.safagurdag.repository.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity

@Dao
internal abstract class WeatherDetailDao {

    @Query("SELECT * FROM WeatherDetail ORDER BY timestamp DESC")
    abstract suspend fun getWeatherDetail():WeatherDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveWeatherDetail(weatherDetail:WeatherDetailEntity)


}