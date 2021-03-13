package com.itis.template.presentation.main

import android.location.Location
import com.itis.template.data.LocationRepositoryImpl
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import ru.kpfu.stud.weather.data.api.response.WeatherResponse
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity
import ru.kpfu.stud.weather.domain.FindCityUseCase

class MainPresenter(
    private val findCityUseCase: FindCityUseCase,
    private val locationRepository: LocationRepositoryImpl
) : MvpPresenter<MainView>() {

//    fun onHelloClick() {
//        presenterScope.launch {
//            try {
//                viewState.showLoading()
//                findCityUseCase.findWeatherInCity("Kazan").run {
//                    viewState.showWeather(main.temp.toString())
//                }
//            } catch (throwable: Throwable) {
//                viewState.consumerError(throwable)
//            } finally {
//                viewState.hideLoading()
//            }
//        }
//    }

    fun around(lat: Double, lon: Double): List<WeatherEntity> {
        var list: List<WeatherEntity> = ArrayList<WeatherEntity>()
        presenterScope.launch {
            list = findCityUseCase.findAround(lat, lon)
        }
        return list
    }

    fun onHelloClickName(query: String?) {
        var idCity: Int
        var windSpeedCity: String
        var windDeg: String
        var descr: String
        var tempCity: String
        var cityName: String
        var feel: String
        var entity: WeatherEntity?
        var res: WeatherResponse

        presenterScope.launch {
            if (query != null) {
                findCityUseCase.findWeatherInCity(query!!).run {
                    cityName = name
                    idCity = id
                    windSpeedCity = wind.speed.toInt().toString()
                    windDeg = calcWind(wind.deg)
                    descr = weather[0].description
                    tempCity = main.temp.toInt().toString()
                    feel = main.feelsLike.toInt().toString()
                }
            }
        }
        //if (isNetworkEnabled) {
//            lifecycleScope.launch {
//                query?.let {
//                    api.getWeather(it).run {
//                        cityName = name
//                        idCity = id
//                        windSpeedCity = wind.speed.toInt().toString()
//                        windDeg = calcWind(wind.deg)
//                        descr = weather[0].description
//                        tempCity = main.temp.toInt().toString()
//                        feel = main.feelsLike.toInt().toString()
//                        entity = WeatherEntity(1, name, id, coord.lat, coord.lon, tempCity.toInt(), descr, feel, windSpeedCity, windDeg)
//                        if (entity != null) repo.saveBase(entity!!)
//                        else Toast.makeText(applicationContext, "Город не найден", Toast.LENGTH_SHORT).show()
//                    }
//                    val intent = Intent(applicationContext, CheckDetailsActivity::class.java)
//                    intent.putExtra("ID", idCity)
//                    intent.putExtra("WIND_SPEED", windSpeedCity)
//                    intent.putExtra("DESCR", descr)
//                    intent.putExtra("TEMP", tempCity)
//                    intent.putExtra("NAME", cityName)
//                    intent.putExtra("WIND_DEG", windDeg)
//                    intent.putExtra("FEEL", feel)
//                    startActivity(intent)
//                }
//            }
        //} else Toast.makeText(applicationContext, "Нет подключения к Интернету", Toast.LENGTH_SHORT).show()
    }

    fun onHelloClickAroundNetworkDisable(): List<WeatherEntity> {
        var list = ArrayList<WeatherEntity>()
        presenterScope.launch {
            list.addAll((findCityUseCase.findDBAround()))
        }
        return list
    }

    fun onHelloClickNetworkDisable(id: Int): WeatherEntity {
        var resp: WeatherEntity? = null
        presenterScope.launch {
            resp = findCityUseCase.findWeatherInCityByIdFromDB(id)
        }
        return resp!!
    }

    fun onLocationClick(): Location? {
        var location: Location? = null
        presenterScope.launch {
            location = locationRepository.getUserLocation()/*.also {
                viewState.showUserLocation(it)*/
        }
        return location
    }

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
