package com.safagurdag.repository.entities.weather_detail


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherEntity(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)