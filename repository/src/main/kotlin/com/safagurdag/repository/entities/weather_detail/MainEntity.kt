package com.safagurdag.repository.entities.weather_detail


import com.squareup.moshi.JsonClass
import kotlin.math.roundToInt

@JsonClass(generateAdapter = true)
data class MainEntity(
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

/**
 * The given temperature is being converted to celcius
 */
fun MainEntity.getTemperatureInCelcius(): String = "${temp.minus(273.15).roundToInt()} °C"

