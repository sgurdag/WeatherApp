package com.safagurdag.repository.network

import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject


internal const val TIMEOUT_DURATION = 7L

internal class ApiImpl @Inject constructor() : Api {

    //region Properties

    private val service by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .addInterceptor(
                        Interceptor.invoke { chain ->

                            //The final request urls are manipulated to add the appid parameter

                            val original = chain.request()
                            val originalHttpUrl = original.url
                            val url = originalHttpUrl.newBuilder()
                                .addQueryParameter("appid", "b1a3d86e0d0ab3d3046a2bc6fda09c8f")
                                .build()

                            val requestBuilder = original.newBuilder().url(url)

                            val request = requestBuilder.build()

                            chain.proceed(request)
                        }
                    )
                    .build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(Api::class.java)
    }
    //endregion

    //region Functions

    override suspend fun getWeatherDetails(lat:String,long:String): Response<WeatherDetailEntity> {
        return service.getWeatherDetails(lat,long)
    }

    //endregion
}
