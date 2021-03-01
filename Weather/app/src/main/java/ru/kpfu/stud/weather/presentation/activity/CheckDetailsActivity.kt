package ru.kpfu.stud.weather.presentation.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_check_details.*
import kotlinx.coroutines.launch
import ru.kpfu.stud.weather.R
import ru.kpfu.stud.weather.data.ConnectionValidator
import ru.kpfu.stud.weather.data.WeatherRepositoryImpl
import ru.kpfu.stud.weather.data.api.ApiFactory
import ru.kpfu.stud.weather.domain.WeatherRepository

class CheckDetailsActivity : AppCompatActivity() {
    lateinit var repo: WeatherRepository

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_details)
        repo = WeatherRepositoryImpl(ApiFactory.weatherApi, this)
        var isNetworkEnabled = ConnectionValidator().verifyAvailableNetwork(this)
        if (isNetworkEnabled) {
            if (intent != null) {
                temp_view.text = intent.getStringExtra("TEMP") + '°'
                descr.text = intent.getStringExtra("DESCR")
                city_view.text = intent.getStringExtra("NAME")
                wind_view.text = intent.getStringExtra("WIND_SPEED") + " м/с, " + intent.getStringExtra("WIND_DEG")
                feel_view.text = intent.getStringExtra("FEEL") + '°'
            }
        } else {
            lifecycleScope.launch {
                var idCity: Int = -1
                if (intent != null) {
                    idCity = intent.getIntExtra("ID", 1)
                }
                var myCity = repo.getCityById(idCity)
                if (myCity != null) {
                    with(myCity) {
                        temp_view.text = temp.toString() + '°'
                        descr.text = description
                        city_view.text = name
                        wind_view.text = speed + " м/с, " + direction
                        feel_view.text = feel + '°'
                    }
                }
            }
        }
    }

}