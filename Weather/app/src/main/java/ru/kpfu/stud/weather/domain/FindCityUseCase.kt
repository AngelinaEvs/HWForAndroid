package ru.kpfu.stud.weather.domain

import kotlinx.coroutines.withContext
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

}