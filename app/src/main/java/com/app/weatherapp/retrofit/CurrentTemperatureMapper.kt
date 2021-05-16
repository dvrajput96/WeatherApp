package com.app.weatherapp.retrofit

import com.app.weatherapp.model.CurrentTemperatureData
import com.app.weatherapp.util.EntityMapper
import javax.inject.Inject

class CurrentTemperatureMapper
@Inject
constructor() : EntityMapper<CurrentTemperatureResp, CurrentTemperatureData> {

    override fun mapFromEntity(entity: CurrentTemperatureResp?): CurrentTemperatureData? {
        return CurrentTemperatureData(
            base = entity?.base.toString(),
            clouds = CurrentTemperatureData.Clouds(
                all = entity?.clouds?.all ?: 0
            ),
            cod = entity?.cod ?: 0,
            coord = CurrentTemperatureData.Coord(
                entity?.coord?.lat ?: 0.0,
                entity?.coord?.lon ?: 0.0
            ),
            dt = entity?.dt ?: 0,
            id = entity?.id ?: 0,
            main = CurrentTemperatureData.Main(
                entity?.main?.feels_like ?: 0.0,
                entity?.main?.humidity ?: 0,
                entity?.main?.pressure ?: 0,
                entity?.main?.temp ?: 0.0,
                entity?.main?.temp_max ?: 0.0,
                entity?.main?.temp_min ?: 0.0
            ),
            name = entity?.name.toString(),
            sys = CurrentTemperatureData.Sys(
                entity?.sys?.country.toString(),
                entity?.sys?.id ?: 0,
                entity?.sys?.sunrise ?: 0,
                entity?.sys?.sunset ?: 0,
                entity?.sys?.type ?: 0
            ),
            timezone = entity?.timezone ?: 0,
            visibility = entity?.visibility ?: 0,
            weather = entity?.weather?.map {
                CurrentTemperatureData.Weather(
                    it?.description.toString(),
                    it?.icon.toString(),
                    it?.id ?: 0,
                    it?.main.toString()
                )
            },
            wind = CurrentTemperatureData.Wind(
                entity?.wind?.deg ?: 0,
                entity?.wind?.speed ?: 0.0
            )
        )
    }

    override fun mapToEntity(domainModel: CurrentTemperatureData?): CurrentTemperatureResp? {
        return null
    }

    fun mapCurrentTempData(currentTemperatureResp: CurrentTemperatureResp?): CurrentTemperatureData? {
        return mapFromEntity(currentTemperatureResp)
    }
}

