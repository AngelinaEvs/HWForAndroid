package ru.kpfu.stud.weather.domain

import kotlinx.coroutines.withContext
import ru.kpfu.stud.weather.data.WeatherRepositoryImpl
import ru.kpfu.stud.weather.data.api.response.WeatherResponse
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity
import kotlin.coroutines.CoroutineContext

class FindCityUseCase(
    private val weatherRepository: WeatherRepository,
    private val context: CoroutineContext
) {

    suspend fun findWeatherInCity(weatherEntity: WeatherEntity) =
            withContext(context) {
                weatherRepository.saveBase(weatherEntity)
            }

    suspend fun findWeatherInCity(name: String): WeatherResponse =
        withContext(context) {
            weatherRepository.getWeather(name)
        }

    suspend fun findAround(lat: Double, lon: Double): List<WeatherEntity> =
        withContext(context) {
            weatherRepository.saveAroundLocation(lat, lon)
        }

    suspend fun findDBAround(): List<WeatherEntity> =
        withContext(context) {
            weatherRepository.getAroundCity()
        }

    suspend fun findWeatherInCityByIdFromDB(id: Int): WeatherEntity =
        withContext(context) {
            weatherRepository.getCityById(id)
        }

    suspend fun findWeatherInCityByIdUpdate(id: Int): WeatherResponse =
        withContext(context) {
            weatherRepository.getWeatherAroundById(id)
        }

    suspend fun findWeatherInBase(): WeatherEntity =
        withContext(context) {
            weatherRepository.getBase()!!
        }

}