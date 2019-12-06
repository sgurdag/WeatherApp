package com.safagurdag.repository.entities.weather_detail

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat

@Entity(tableName = "WeatherDetail")
data class WeatherDetailEntity(
    val base: String,
    val clouds: CloudsEntity = getCloudsEmptyEntity(),
    val cod: Int,
    val coord: CoordEntity,
    val dt: Int,
    val id: Int,
    val main: MainEntity,
    val name: String,
    val sys: SysEntity,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherEntity>,
    val wind: WindEntity,
    @PrimaryKey
    var timestamp: Long?
)

fun WeatherDetailEntity.getTimeStamp(): String {
    return run {
        val formatter = SimpleDateFormat("HH:mm:ss")
        formatter.format(timestamp).toString()
    }
}

