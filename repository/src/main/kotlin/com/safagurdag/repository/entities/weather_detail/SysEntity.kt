package com.safagurdag.repository.entities.weather_detail


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SysEntity(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)