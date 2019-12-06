package com.safagurdag.repository.persistence.converters.weather_detail

import com.safagurdag.repository.entities.weather_detail.CoordEntity
import com.safagurdag.repository.entities.weather_detail.CoordEntityJsonAdapter
import com.safagurdag.repository.persistence.converters.BaseConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class CoordConverter : BaseConverter<CoordEntity>() {
    override fun getAdapter(moshi: Moshi): JsonAdapter<CoordEntity> = CoordEntityJsonAdapter(moshi)

}