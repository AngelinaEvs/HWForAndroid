package com.itis.template.recycler

import com.itis.template.WeatherResponse

object CityObjects {
    var cityObjects = ArrayList<WeatherResponse>()

    fun addItem(city: WeatherResponse) = cityObjects.add(city)

    fun clear() = cityObjects.clear()
}