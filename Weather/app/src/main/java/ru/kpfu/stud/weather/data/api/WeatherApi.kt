package ru.kpfu.stud.weather.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.kpfu.stud.weather.data.api.response.WeatherResponse

interface WeatherApi {

    @GET("weather?units=metric&lang=ru")
    suspend fun getWeather(@Query("id") cityId: Int) : WeatherResponse

    @GET("weather?units=metric&lang=ru")
    suspend fun getWeather(@Query("q") cityName: String) : WeatherResponse

    @GET("find?units=metric&lang=ru&cnt=15")
    suspend fun getSquareWeather(@Query("lat") lat: Double, @Query("lon") lon: Double) : WeatherResponse

}
