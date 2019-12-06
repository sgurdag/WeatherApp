package com.safagurdag.repository.sources

import android.net.NetworkInfo
import com.safagurdag.repository.R
import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.safagurdag.repository.interactors.base.*
import com.safagurdag.repository.network.ApiImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import javax.inject.Provider

@UseExperimental(ExperimentalCoroutinesApi::class)
class NetworkSourceTest {

    private val apiImpl = mockk<ApiImpl>(relaxed = true)
    private val networkInfoProvider = mockk<Provider<NetworkInfo>>(relaxed = true) {
        every { get() } returns mockk(relaxed = true)
    }

    private val source = spyk(NetworkSource(apiImpl, networkInfoProvider))

    @Nested
    inner class GetWeatherDetails {

        @Test
        fun `should return network error when internet is not connected`() {
            every { networkInfoProvider.get().isConnected } returns false

            runBlockingTest {
                val result = source.getWeatherDetails(any(), any())

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf NetworkError::class
                }
            }
        }

        @Test
        fun `should return response error when it is not successful`() {
            every { networkInfoProvider.get().isConnected } returns true
            coEvery { apiImpl.getWeatherDetails(any(), any()).isSuccessful } returns false

            runBlockingTest {
                val result = source.getWeatherDetails(any(), any())

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf ResponseError::class
                    (it as ResponseError).messageRes shouldEqualTo R.string.reason_response
                }
            }
        }

        @Test
        fun `should return empty result error when body is null`() {
            every { networkInfoProvider.get().isConnected } returns true
            coEvery { apiImpl.getWeatherDetails(any(), any()).isSuccessful } returns true
            coEvery { apiImpl.getWeatherDetails(any(), any()).body() } returns null

            runBlockingTest {
                val result = source.getWeatherDetails(any(), any())

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf EmptyResultError::class
                }
            }
        }

        @Test
        fun `should return success with data if execution is successful`() {

            val weatherDetailEntity: WeatherDetailEntity = mockk()

            every { networkInfoProvider.get().isConnected } returns true
            coEvery { apiImpl.getWeatherDetails(any(), any()).isSuccessful } returns true
            coEvery { apiImpl.getWeatherDetails(any(), any()).body() } returns weatherDetailEntity

            runBlockingTest {
                val result = source.getWeatherDetails(any(), any())

                result shouldBeInstanceOf Success::class
            }
        }
    }
}
