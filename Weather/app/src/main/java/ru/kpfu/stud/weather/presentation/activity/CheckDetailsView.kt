package ru.kpfu.stud.weather.presentation.activity

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity

@AddToEndSingle
interface CheckDetailsView : MvpView {

    fun show()

    fun showNoInternet(we: WeatherEntity)

}