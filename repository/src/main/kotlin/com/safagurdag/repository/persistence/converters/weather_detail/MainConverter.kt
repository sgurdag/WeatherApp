package com.safagurdag.repository.persistence.converters.weather_detail

import com.safagurdag.repository.entities.weather_detail.MainEntity
import com.safagurdag.repository.entities.weather_detail.MainEntityJsonAdapter
import com.safagurdag.repository.persistence.converters.BaseConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class MainConverter : BaseConverter<MainEntity>() {
    override fun getAdapter(moshi: Moshi): JsonAdapter<MainEntity> = MainEntityJsonAdapter(moshi)
}