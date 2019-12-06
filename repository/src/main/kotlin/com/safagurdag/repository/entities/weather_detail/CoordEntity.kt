package com.safagurdag.repository.entities.weather_detail


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordEntity(
    val lat: Double,
    val lon: Double
)