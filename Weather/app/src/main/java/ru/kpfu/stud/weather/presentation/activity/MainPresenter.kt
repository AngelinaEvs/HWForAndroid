package ru.kpfu.stud.weather.presentation.activity

import android.location.Location
import ru.kpfu.stud.weather.data.LocationRepositoryImpl
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

    fun onButtonClick() {
        presenterScope.launch {
            viewState.showDetails(findCityUseCase.findWeatherInBase())
        }
    }

    fun around(lat: Double, lon: Double): List<WeatherEntity> {
        var list: List<WeatherEntity> = ArrayList<WeatherEntity>()
        viewState.showLoading()
        presenterScope.launch {
            list = findCityUseCase.findAround(lat, lon)
            viewState.showWeather(list)
        }
        return list
    }

    fun onHelloClickName(query: String?): WeatherEntity? {
        var entity: WeatherEntity? = null
        try {
            viewState.showLoading()
            presenterScope.launch {
            if (query != null) {
                findCityUseCase.findWeatherInCity(query!!).run {
                    entity = WeatherEntity(1, name, id, coord.lat, coord.lon, main.temp.toInt(), weather[0].description,
                        wind.speed.toInt().toString(), main.feelsLike.toInt().toString(), calcWind(wind.deg))
                }
                viewState.showDetails(entity!!)
            }
            }
        } catch (throwable: Throwable) {
            viewState.consumerError(throwable)
        } finally {
            viewState.hideLoading()
        }
        return entity
    }

    fun onHelloClickId(id: Int): WeatherEntity? {
        var entity: WeatherEntity? = null
        try {
            viewState.showLoading()
            presenterScope.launch {
                if (id != null) {
                    findCityUseCase.findWeatherInCityByIdUpdate(id!!).run {
                        entity = WeatherEntity(1, name, id, coord.lat, coord.lon, main.temp.toInt(), weather[0].description,
                            wind.speed.toInt().toString(), main.feelsLike.toInt().toString(), calcWind(wind.deg))
                    }
                    viewState.showDetails(entity!!)
                }
            }
        } catch (throwable: Throwable) {
            viewState.consumerError(throwable)
        } finally {
            viewState.hideLoading()
        }
        return entity
    }

    fun onHelloClickAroundNetworkDisable(): List<WeatherEntity> {
        var list = ArrayList<WeatherEntity>()
        presenterScope.launch {
            list.addAll((findCityUseCase.findDBAround()))
            viewState.showWeather(list)
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
            location = locationRepository.getUserLocation().also {
                viewState.showUserLocation(it)
            }
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
