package com.safagurdag.repository.sources

import android.content.Context
import com.safagurdag.repository.MILISECONDS_FOR_25_HOURS
import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.safagurdag.repository.interactors.base.*
import com.safagurdag.repository.persistence.WeatherDatabase
import io.mockk.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class PersistenceSourceTest {

    private val ctx = mockk<Context>(relaxed = true)
    private val dbImplementation = mockk<WeatherDatabase>(relaxed = true)
    private val source = spyk(PersistenceSource(ctx))

    private val scope = CoroutineScope(Dispatchers.IO)

    @BeforeEach
    fun setup() {
        mockkObject(WeatherDatabase)
        every { WeatherDatabase.getInstance(ctx) } returns dbImplementation
    }

    @Nested
    inner class GetWeatherDetails {

        @Test
        fun `should return persistance empty error when db is empty`() {
            coEvery { dbImplementation.weatherDetailDao.getWeatherDetail() } returns null

            scope.launch {
                val result = source.getWeatherDetails(any(), any() )

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf PersistenceEmpty::class
                }
            }
        }

        @Test
        fun `should return success with data if db is not empty`() {

            val weatherDetailEntity: WeatherDetailEntity = mockk()

            coEvery { dbImplementation.weatherDetailDao.getWeatherDetail() } returns weatherDetailEntity

            scope.launch {
                val result = source.getWeatherDetails(any(), any() )

                result shouldBeInstanceOf Success::class
            }
        }

        @Test
        fun `should return persistence data is expired error if data is older than 24 hours`() {

            val weatherDetailEntity: WeatherDetailEntity = mockk(relaxed = true)
            weatherDetailEntity.timestamp = Calendar.getInstance().timeInMillis - MILISECONDS_FOR_25_HOURS

                coEvery { dbImplementation.weatherDetailDao.getWeatherDetail() } returns weatherDetailEntity

            scope.launch {
                val result = source.getWeatherDetails(any(), any() )

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf PersistenceDataExpired::class
                }
            }
        }
    }
}
