package com.safagurdag.repository.persistence.converters.weather_detail

import com.safagurdag.repository.entities.weather_detail.SysEntity
import com.safagurdag.repository.entities.weather_detail.SysEntityJsonAdapter
import com.safagurdag.repository.persistence.converters.BaseConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class SysConverter : BaseConverter<SysEntity>() {
    override fun getAdapter(moshi: Moshi): JsonAdapter<SysEntity> = SysEntityJsonAdapter(moshi)
}