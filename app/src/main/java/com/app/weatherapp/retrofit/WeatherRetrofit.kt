package com.app.weatherapp.retrofit

import retrofit2.http.GET

interface WeatherRetrofit {

    @GET("/data/2.5/weather?q=Bengaluru&mode=json&units=metric&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getCurrentTemperature(): CurrentTemperatureResp

    @GET("/data/2.5/forecast?q=Bengaluru&mode=json&units=metric&cnt=7&APPID=9b8cb8c7f11c077f8c4e217974d9ee40")
    suspend fun getForecastTemperature(): ForecastTemperatureResp

}