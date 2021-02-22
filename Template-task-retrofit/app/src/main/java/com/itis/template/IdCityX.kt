package com.itis.template


import com.google.gson.annotations.SerializedName

data class IdCityX(
    @SerializedName("city")
    var city: List<CityX>
)

data class CityX(
    @SerializedName("city_id")
    var cityId: String,
    @SerializedName("country_id")
    var countryId: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("region_id")
    var regionId: String
)