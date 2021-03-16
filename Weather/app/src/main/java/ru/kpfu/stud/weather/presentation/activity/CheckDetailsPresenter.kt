package ru.kpfu.stud.weather.presentation.activity

import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import ru.kpfu.stud.weather.domain.FindCityUseCase

class CheckDetailsPresenter(
    private val findCityUseCase: FindCityUseCase
) : MvpPresenter<CheckDetailsView>() {

    fun open() = viewState.show()

    fun openNoInternet(id: Int) {
        presenterScope.launch {
            viewState.showNoInternet(findCityUseCase.findWeatherInCityByIdFromDB(id))
        }
    }

}