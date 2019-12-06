package com.sgurdag.weather_detail.ui

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.viewModelScope
import com.safagurdag.core.base.viewmodel.BaseViewModel
import com.safagurdag.repository.entities.weather_detail.*
import com.safagurdag.repository.interactors.GetWeatherDetails
import com.safagurdag.repository.interactors.base.handle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherDetailViewModel @Inject constructor(
    private val getWeatherDetails: GetWeatherDetails
) : BaseViewModel<WeatherDetailEntity>() {

    //region Properties

    val location = MutableLiveData<Location>()

    val currentCondition = map(successData) {
        it.weather[0].main
    }
    val temperature = map(successData) {
        it.main.getTemperatureInCelcius()
    }
    val windSpeed = map(successData) {
        it.wind.getSpeedWithDenomination()
    }
    val windDirection = map(successData) {
        it.wind.getDirection()
    }
    val weatherIconUrl = map(successData) {
        it.weather[0].icon
    }
    val lastUpdate = map(successData) {
        it.getTimeStamp()
    }
    val locationName = map(successData) {
        it.name
    }


    //endregion

    //region Functions
    override suspend fun loadData() {

    }

    fun getWeatherData() {

        //viewModelScope is used to be able to reach a suspend function in ViewModel

        viewModelScope.launch {
            getWeatherDetails(GetWeatherDetails.Params(location.value?.latitude.toString(),location.value?.longitude.toString())).collect {
                it.handle(::handleState, ::handleFailure, ::handleSuccess)
            }
        }
    }
    //endregion

}
