package com.safagurdag.repository.sources

import android.net.NetworkInfo
import com.safagurdag.repository.Repository
import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.safagurdag.repository.interactors.base.*
import com.safagurdag.repository.network.ApiImpl
import retrofit2.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Provider


internal class NetworkSource @Inject constructor(
    private val apiImpl: ApiImpl,
    private val networkInfoProvider: Provider<NetworkInfo>
) : Repository() {

    //region Properties

    private val isNetworkConnected: Boolean
        get() {
            val networkInfo = networkInfoProvider.get()
            return networkInfo != null && networkInfo.isConnected
        }
    //endregion

    //region Functions

    override suspend fun getWeatherDetails(lat: String, long: String): Result<WeatherDetailEntity> =

        safeExecute({
            apiImpl.getWeatherDetails(lat,long)
        }) { entity ->

            /**
             * While passing the api data to lower levels,
             * the entity model is being manipulated to change imageUrl to be able to use it directly in imageviews.
             * Also, a timestamp is added to Entity to keep the time when the latest date is fetched.
             */

            entity.copy(
                weather = entity.weather.map { weather ->
                    if (weather.icon.isNotBlank()) {
                        weather.copy(
                            icon = transformImageUrl(weather.icon)
                        )
                    } else
                        weather
                },
                timestamp = Calendar.getInstance().timeInMillis
            )
        }

    //endregion

    private inline fun <T, R> safeExecute(
        block: () -> Response<T>,
        transform: (T) -> R
    ) =
        if (isNetworkConnected) {
            try {
                block().extractResponseBody(transform)
            } catch (e: IOException) {
                Failure(TimeoutError())
            }
        } else {
            Failure(NetworkError())
        }

    private inline fun <T, R> Response<T>.extractResponseBody(transform: (T) -> R) =
        if (isSuccessful) {
            body()?.let {
                Success(transform(it))
            } ?: Failure(EmptyResultError())
        } else {
            Failure(ResponseError())
        }

    private fun transformImageUrl(imageUrl: String) =
        try {

            "http://openweathermap.org/img/w/$imageUrl.png"

        } catch (e: Exception) {
            imageUrl
        }
}