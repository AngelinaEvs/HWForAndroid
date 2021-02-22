package com.itis.template

import retrofit2.http.GET

interface CityApi {

    @GET("aZolo77/citiesBase/master/cities.json")
    suspend fun getId() : IdCityX

}