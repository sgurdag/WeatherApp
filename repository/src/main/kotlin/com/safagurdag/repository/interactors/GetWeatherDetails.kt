package com.safagurdag.repository.interactors

import com.safagurdag.repository.entities.weather_detail.WeatherDetailEntity
import com.safagurdag.repository.interactors.base.*
import com.safagurdag.repository.sources.NetworkSource
import com.safagurdag.repository.sources.PersistenceSource
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetWeatherDetails @Inject constructor(): BaseInteractor<WeatherDetailEntity, GetWeatherDetails.Params>() {

    //region Properties

    @field:Inject
    internal lateinit var networkSource: NetworkSource

    @field:Inject
    internal lateinit var persistenceSource: PersistenceSource
    //endregion


    override suspend fun FlowCollector<Result<WeatherDetailEntity>>.run(params: Params) {

        /**
         * Hitting the API and emitting the result
         */
        emit(networkSource
            .getWeatherDetails(params.latitude,params.longitude)
            .also {
                if (it is Success){

                    /**
                     * Saving the fetched data
                     */
                    persistenceSource.saveWeatherDetail(it.successData)
                } else if (it is Failure){

                    /**
                     * in case of network error, we consult persistence source for data and emitting its result
                     */
                    if (it.errorData is NetworkError){
                        emit(persistenceSource.getWeatherDetails(params.latitude,params.longitude))
                    }else
                        emit(it)

                }
            })
    }


    data class Params(
        val latitude: String,
        val longitude: String
    ) : InteractorParameters

}