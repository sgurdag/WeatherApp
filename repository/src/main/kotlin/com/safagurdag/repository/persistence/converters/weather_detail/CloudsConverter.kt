package com.safagurdag.repository.persistence.converters.weather_detail

import com.safagurdag.repository.entities.weather_detail.CloudsEntity
import com.safagurdag.repository.entities.weather_detail.CloudsEntityJsonAdapter
import com.safagurdag.repository.persistence.converters.BaseConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class CloudsConverter : BaseConverter<CloudsEntity>() {

    override fun getAdapter(moshi: Moshi): JsonAdapter<CloudsEntity>  = CloudsEntityJsonAdapter(moshi)
}