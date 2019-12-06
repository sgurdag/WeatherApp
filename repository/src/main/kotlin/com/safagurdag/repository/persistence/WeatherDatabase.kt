package com.safagurdag.repository.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.safagurdag.repository.persistence.converters.weather_detail.*
import com.safagurdag.repository.persistence.dao.WeatherDetailDao

const val DB_NAME = "WeatherDB"

@Database(
    entities = [WeatherDetailEntity::class],
    version = 1
)
@TypeConverters(
    CloudsConverter::class,
    CoordConverter::class,
    MainConverter::class,
    SysConverter::class,
    WeatherConverter::class,
    WindConverter::class
)
internal abstract class WeatherDatabase : RoomDatabase() {

    companion object {

        private lateinit var instance: WeatherDatabase

        fun getInstance(ctx: Context): WeatherDatabase {
            if (!::instance.isInitialized) {
                instance = Room.databaseBuilder(ctx, WeatherDatabase::class.java, DB_NAME)
                    .build()
            }

            return instance
        }
    }

    internal abstract val weatherDetailDao: WeatherDetailDao


}