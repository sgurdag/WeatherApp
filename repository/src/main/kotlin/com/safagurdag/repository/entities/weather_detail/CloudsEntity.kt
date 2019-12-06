package com.safagurdag.repository.entities.weather_detail

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CloudsEntity(
    val all: Int
)
fun getCloudsEmptyEntity():CloudsEntity = CloudsEntity(-1)