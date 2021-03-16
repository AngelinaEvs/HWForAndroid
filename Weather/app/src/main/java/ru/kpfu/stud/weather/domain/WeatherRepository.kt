package ru.kpfu.stud.weather.domain

import android.location.Location
import ru.kpfu.stud.weather.data.api.ApiFactory
import ru.kpfu.stud.weather.data.api.response.WeatherResponse
import ru.kpfu.stud.weather.data.model.dao.WeatherDao
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity

interface WeatherRepository {

    suspend fun getBase(): WeatherEntity?

    suspend fun getAllTable(): List<WeatherEntity>

    suspend fun saveBase(weatherEntity: WeatherEntity)

    suspend fun getLocation(): Location?

    suspend fun saveAroundLocation(lat: Double, lon: Double): List<WeatherEntity>

    suspend fun getAroundCity(): List<WeatherEntity>

    suspend fun deleteAround()

    suspend fun getCityById(id: Int): WeatherEntity

    suspend fun getWeather(name: String): WeatherResponse

    suspend fun updateAround(lat: Double, lon: Double): List<WeatherEntity>

    suspend fun getWeatherAroundById(id: Int): WeatherResponse

}