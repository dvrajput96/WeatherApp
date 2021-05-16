package com.app.weatherapp.retrofit

import com.app.weatherapp.model.ForecastTemperatureData
import com.app.weatherapp.util.EntityMapper
import javax.inject.Inject

class ForecastTemperatureMapper
@Inject
constructor() : EntityMapper<ForecastTemperatureResp, ForecastTemperatureData> {

    override fun mapFromEntity(entity: ForecastTemperatureResp?): ForecastTemperatureData? {
        return ForecastTemperatureData(
            ForecastTemperatureData.City(
                ForecastTemperatureData.City.Coord(
                    entity?.city?.coord?.lat ?: 0.0,
                    entity?.city?.coord?.lon ?: 0.0
                ),
                entity?.city?.country.toString(),
                entity?.city?.id ?: 0,
                entity?.city?.name.toString(),
                entity?.city?.population ?: 0,
                entity?.city?.sunrise ?: 0,
                entity?.city?.sunset ?: 0,
                entity?.city?.timezone ?: 0
            ),
            entity?.cnt ?: 0,
            entity?.cod.toString(),
            entity?.list?.map {
                ForecastTemperatureData.Temperature(
                    ForecastTemperatureData.Temperature.Clouds(
                        it?.clouds?.all ?: 0
                    ),
                    it?.dt ?: 0,
                    it?.dt_txt.toString(),
                    ForecastTemperatureData.Temperature.Main(
                        it?.main?.feels_like ?: 0.0,
                        it?.main?.grnd_level ?: 0,
                        it?.main?.humidity ?: 0,
                        it?.main?.pressure ?: 0,
                        it?.main?.sea_level ?: 0,
                        it?.main?.temp ?: 0.0,
                        it?.main?.temp_kf ?: 0.0,
                        it?.main?.temp_max ?: 0.0,
                        it?.main?.temp_min ?: 0.0
                    ),
                    it?.pop ?: 0.0,
                    ForecastTemperatureData.Temperature.Rain(
                        it?.rain?.threeH ?: 0.0
                    ),
                    ForecastTemperatureData.Temperature.Sys(
                        it?.sys?.pod.toString()
                    ),
                    it?.visibility ?: 0,
                    it?.weather?.map { weather ->
                        ForecastTemperatureData.Temperature.Weather(
                            weather?.description.toString(),
                            weather?.icon.toString(),
                            weather?.id ?: 0,
                            weather?.main.toString()
                        )
                    },
                    ForecastTemperatureData.Temperature.Wind(
                        it?.wind?.deg ?: 0,
                        it?.wind?.gust ?: 0.0,
                        it?.wind?.speed ?: 0.0
                    )
                )
            },
            entity?.message ?: 0
        )
    }

    override fun mapToEntity(domainModel: ForecastTemperatureData?): ForecastTemperatureResp? {
        return null
    }

    fun mapForecastTempData(forecastTemperatureResp: ForecastTemperatureResp?): ForecastTemperatureData? {
        return mapFromEntity(forecastTemperatureResp)
    }
}

