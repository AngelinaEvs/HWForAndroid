package com.itis.template.presentation.main

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface MainView : MvpView {

    fun checkLocationPermission(): Boolean

    fun isNetEnabled(): Boolean

    fun showLoading()

    fun hideLoading()

    @Skip
    fun consumerError(throwable: Throwable)

    fun showWeather(lat: Double, lon: Double)//(temperature: String)

    fun showUserLocation(location: Location)

    @OneExecution
    fun navigateToLogin()
}
