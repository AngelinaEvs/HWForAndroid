package ru.kpfu.stud.weather.presentation.activity

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_check_details.*
import kotlinx.coroutines.Dispatchers
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kpfu.stud.weather.R
import ru.kpfu.stud.weather.data.ConnectionValidator
import ru.kpfu.stud.weather.data.WeatherRepositoryImpl
import ru.kpfu.stud.weather.data.api.ApiFactory
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity
import ru.kpfu.stud.weather.domain.FindCityUseCase

class CheckDetailsActivity : MvpAppCompatActivity(), CheckDetailsView {
    @InjectPresenter
    lateinit var presenter: CheckDetailsPresenter

    @ProvidePresenter
    fun presenter(): CheckDetailsPresenter = initPresenter()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_details)
        initPresenter()
        var isNetworkEnabled = ConnectionValidator().verifyAvailableNetwork(this)
        if (intent != null) {
            var i = intent.getStringExtra("ID").toInt()
            if (isNetworkEnabled) presenter.open()
            else presenter.openNoInternet(i)
        }
    }

    override fun show() {
        if (intent != null) {
            temp_view.text = intent.getStringExtra("TEMP") + '°'
            descr.text = intent.getStringExtra("DESCR")
            city_view.text = intent.getStringExtra("NAME")
            wind_view.text = intent.getStringExtra("WIND_SPEED") + " м/с, " + intent.getStringExtra("WIND_DEG")
            feel_view.text = intent.getStringExtra("FEEL") + '°'
        }
    }

    override fun showNoInternet(we: WeatherEntity) {
        if (we != null) {
            with(we) {
                temp_view.text = temp.toString() + '°'
                descr.text = description
                city_view.text = name
                wind_view.text = speed + " м/с, " + direction
                feel_view.text = feel + '°'
            }
        }
    }

    private fun initPresenter() = CheckDetailsPresenter(
        findCityUseCase = FindCityUseCase(WeatherRepositoryImpl(ApiFactory.weatherApi, applicationContext),
            Dispatchers.IO)
    )

}