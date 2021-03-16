package ru.kpfu.stud.weather.presentation.activity

import android.location.Location
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity

@AddToEndSingle
interface MainView : MvpView {

    fun checkLocationPermission(): Boolean

    fun isNetEnabled(): Boolean

    fun showLoading()

    fun hideLoading()

    @Skip
    fun consumerError(throwable: Throwable)

    fun showWeather(list: List<WeatherEntity>)

    fun showUserLocation(location: Location)

    fun showDetails(entity: WeatherEntity)
}
