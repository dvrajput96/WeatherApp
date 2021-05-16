package com.app.weatherapp.repository

import com.app.weatherapp.model.CurrentTemperatureData
import com.app.weatherapp.model.ForecastTemperatureData
import com.app.weatherapp.retrofit.CurrentTemperatureMapper
import com.app.weatherapp.retrofit.ForecastTemperatureMapper
import com.app.weatherapp.retrofit.WeatherRetrofit
import com.app.weatherapp.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val weatherRetrofit: WeatherRetrofit,
    private val currentTemperatureMapper: CurrentTemperatureMapper,
    private val forecastTemperatureMapper: ForecastTemperatureMapper,
) {
    suspend fun getCurrentTemperature(): Flow<DataState<CurrentTemperatureData>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val currentTemperatureResp = weatherRetrofit.getCurrentTemperature()
            val currentTemperatureData = currentTemperatureMapper.mapCurrentTempData(currentTemperatureResp)
            currentTemperatureData?.let {
                emit(DataState.Success(currentTemperatureData))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getForecastTemperature(): Flow<DataState<ForecastTemperatureData>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val forecastTemperatureResp = weatherRetrofit.getForecastTemperature()
            val forecastTemperatureData = forecastTemperatureMapper.mapForecastTempData(forecastTemperatureResp)
            forecastTemperatureData?.let {
                emit(DataState.Success(forecastTemperatureData))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}