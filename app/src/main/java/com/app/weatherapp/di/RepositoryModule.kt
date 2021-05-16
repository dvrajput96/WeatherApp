package com.app.weatherapp.di

import com.app.weatherapp.repository.MainRepository
import com.app.weatherapp.retrofit.CurrentTemperatureMapper
import com.app.weatherapp.retrofit.ForecastTemperatureMapper
import com.app.weatherapp.retrofit.WeatherRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogRetrofit: WeatherRetrofit,
        currentTemperatureMapper: CurrentTemperatureMapper,
        forecastTemperatureMapper: ForecastTemperatureMapper
    ): MainRepository {
        return MainRepository(blogRetrofit, currentTemperatureMapper,forecastTemperatureMapper)
    }
}