package com.safagurdag.repository.persistence.converters.weather_detail

import com.safagurdag.repository.entities.weather_detail.WeatherEntity
import com.safagurdag.repository.persistence.converters.BaseListConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class WeatherConverter : BaseListConverter<WeatherEntity>() {
    override fun getAdapter(moshi: Moshi): JsonAdapter<List<WeatherEntity>> = moshi.adapter(
        Types.newParameterizedType(
            List::class.java,
            WeatherEntity::class.java
        )
    )
}