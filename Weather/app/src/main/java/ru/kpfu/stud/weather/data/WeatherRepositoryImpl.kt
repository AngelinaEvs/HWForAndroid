package ru.kpfu.stud.weather.data

import android.content.Context
import android.location.Location
import android.widget.Toast
import ru.kpfu.stud.weather.data.api.WeatherApi
import ru.kpfu.stud.weather.data.api.response.Weather
import ru.kpfu.stud.weather.data.api.response.WeatherResponse
import ru.kpfu.stud.weather.data.model.AppDatabase
import ru.kpfu.stud.weather.data.model.dao.WeatherDao
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity
import ru.kpfu.stud.weather.domain.WeatherRepository

class WeatherRepositoryImpl(private val weatherApi: WeatherApi, context: Context) : WeatherRepository {
    private var db: AppDatabase = AppDatabase(context)

    override suspend fun getWeather(cityName: String): WeatherResponse {
        val result = weatherApi.getWeather(cityName)
        saveBase(
            WeatherEntity(1, result.name, result.id, result.coord.lat, result.coord.lon,
        result.main.temp.toInt(), result.weather[0].description, result.main.feelsLike.toInt().toString(),
                result.wind.speed.toInt().toString(), calcWind(result.wind.deg))
        )
        return result
    }

    override suspend fun getWeatherAroundById(id: Int): WeatherResponse {
        val result = weatherApi.getWeather(id)
        saveBase(
            WeatherEntity(0, result.name, result.id, result.coord.lat, result.coord.lon,
                result.main.temp.toInt(), result.weather[0].description, result.main.feelsLike.toInt().toString(),
                result.wind.speed.toInt().toString(), calcWind(result.wind.deg))
        )
        return result
    }

    override suspend fun getBase(): WeatherEntity? = db.weatherDao().getBaseCity()

    override suspend fun saveBase(weatherEntity: WeatherEntity) {
        db.weatherDao().deleteBase()
        db.weatherDao().saveBase(weatherEntity)
    }

    override suspend fun getLocation(): Location? {
        return null
    }

    override suspend fun updateAround(lat: Double, lon: Double): List<WeatherEntity> {
        var list: ArrayList<WeatherResponse>
        var listAll = ArrayList<WeatherEntity>()
        weatherApi.getSquareWeather(lat, lon).run {
            list = ArrayList(this.list)
            for (i in 0 until list.size) {
                if (i == 0) listAll.clear()
                with(list[i]) {
                    listAll.add(WeatherEntity(0, name, id, coord.lat, coord.lon, main.temp.toInt(), weather[0].description, main.feelsLike.toInt().toString(), wind.speed.toInt().toString(), calcWind(wind.deg)))
                }
            }
        }
        return listAll
    }


    override suspend fun saveAroundLocation(lat: Double, lon: Double): List<WeatherEntity> {
        var list = updateAround(lat, lon)
        db.weatherDao().clearTableForAround()
        db.weatherDao().saveAroundList(list)
        return list
    }

    override suspend fun getAllTable(): List<WeatherEntity> = db.weatherDao().getAllTable()

    override suspend fun getAroundCity(): List<WeatherEntity> = db.weatherDao().getAroundCity()

    override suspend fun deleteAround() = db.weatherDao().clearTableForAround()

    override suspend fun getCityById(id: Int): WeatherEntity = db.weatherDao().getCityById(id)

    private fun calcWind(wind: Int): String {
        if (wind >= 338) return "северный"
        if (wind <= 22) return "северный"
        if (wind >= 23 && wind <= 67) return "северно-восточный"
        if (wind >= 68 && wind <= 112) return "восточный"
        if (wind >= 113 && wind <= 157) return "юго-восточный"
        if (wind >= 158 && wind <= 202) return "южный"
        if (wind >= 203 && wind <= 247) return "юго-западный"
        if (wind >= 248 && wind <= 292) return "западный"
        if (wind >= 293 && wind <= 337) return "северо-западный"
        return ""
    }
}
