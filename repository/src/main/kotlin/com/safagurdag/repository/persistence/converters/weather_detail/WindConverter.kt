package com.safagurdag.repository.persistence.converters.weather_detail

import com.safagurdag.repository.entities.weather_detail.WindEntity
import com.safagurdag.repository.entities.weather_detail.WindEntityJsonAdapter
import com.safagurdag.repository.persistence.converters.BaseConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class WindConverter : BaseConverter<WindEntity>() {
    override fun getAdapter(moshi: Moshi): JsonAdapter<WindEntity> = WindEntityJsonAdapter(moshi)
}