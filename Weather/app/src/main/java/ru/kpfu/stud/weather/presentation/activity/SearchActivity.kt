package com.itis.template

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.itis.template.data.LocationRepositoryImpl
import com.itis.template.presentation.main.MainPresenter
import com.itis.template.presentation.main.MainView
import com.itis.template.utils.getErrorMessage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import ru.kpfu.stud.weather.R
import kotlinx.coroutines.launch
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kpfu.stud.weather.data.ConnectionValidator
import ru.kpfu.stud.weather.data.MyLocation
import ru.kpfu.stud.weather.data.WeatherRepositoryImpl
import ru.kpfu.stud.weather.data.api.ApiFactory
import ru.kpfu.stud.weather.data.api.response.WeatherResponse
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity
import ru.kpfu.stud.weather.domain.FindCityUseCase
import ru.kpfu.stud.weather.domain.WeatherRepository
import ru.kpfu.stud.weather.presentation.activity.CheckDetailsActivity
import ru.kpfu.stud.weather.presentation.rv.CityAdapter


class SearchActivity : MvpAppCompatActivity(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = initPresenter()

//    private var lat: Double = 0.0
//    private var lon: Double = 0.0
//    private var permission: Array<out String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
//    val PERMISSION_REQUEST_CODE = 1001
//    var listAll = ArrayList<WeatherEntity>()
//    private val api = ApiFactory.weatherApi
//    private var flag: Boolean = false
//    var adapter : CityAdapter? = null
//
//    var success: Boolean = true
//    var isNetworkEnabled = false
//
//    lateinit var mLastLocation: Location
//    private lateinit var mLocationRequest: LocationRequest
//    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
//    var first = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPresenter()
        initListeners()
        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        var isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isNetworkEnabled) {
            presenter.onHelloClickAroundNetworkDisable()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!checkLocationPermission()) requestPermissions(
                    permission,
                    PERMISSION_REQUEST_CODE
                )
                else {
                    if (isGPSEnabled && first) mFusedLocationProviderClient!!.requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback,
                        Looper.myLooper()
                    )
                    var locat = presenter.onLocationClick()
                    first = true
                    if (locat != null) {
                        lat = locat.latitude
                        lon = locat.longitude
                    }
                    listAll.clear()
                    if (isNetworkEnabled) listAll.addAll(presenter.around(lat, lon))
                    else listAll.addAll(presenter.onHelloClickAroundNetworkDisable())
                    forRV()
                }
            }
        }

//    } else {
//        forRV()
//        aroundNetworkDisabled()
//    }
    }

    private fun initPresenter() = MainPresenter(
        findCityUseCase = ApiFactory.weatherApi.let {
            WeatherRepositoryImpl(it, applicationContext).let {
                FindCityUseCase(it, Dispatchers.IO)
            }
        },
        locationRepository = LocationRepositoryImpl(
            client = LocationServices.getFusedLocationProviderClient(this)
        )
    )

    private fun initListeners() {
        search.setOnClickListener {
            search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    presenter.onHelloClickName(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
    }

    override fun checkLocationPermission(): Boolean {
        Toast.makeText(applicationContext, "aaaaa", Toast.LENGTH_SHORT).show()
        return checkMyPermission(permission)
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
    }

    override fun consumerError(throwable: Throwable) {
        Snackbar.make(
            findViewById(android.R.id.content),
            throwable.getErrorMessage(resources),
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showWeather(lat: Double, lon: Double) {
        listAll.clear()
        listAll.addAll(presenter.around(lat, lon))
        adapter?.updateData(listAll)
        rv.adapter = CityAdapter(listAll) { id -> onHelloClick(id) }
    }

    override fun showUserLocation(location: Location) {
        TODO("Not yet implemented")
    }

    override fun navigateToLogin() {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isNetEnabled(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        //repo = WeatherRepositoryImpl(ApiFactory.weatherApi, this)
//        lifecycleScope.launch {
//            listAll.addAll(repo.getAroundCity())
//        }

//        var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//        var isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//        isNetworkEnabled = ConnectionValidator().verifyAvailableNetwork(this)
//        if (isNetworkEnabled) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!checkPermission(permission)) requestPermissions(permission, PERMISSION_REQUEST_CODE)
//                else {
//                    if (isGPSEnabled && first) mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())
//                    LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnCompleteListener {
//                        var lat: Double
//                        var lon: Double
//                        if (it != null) {
//                            while (!it.isComplete) {
//
//                            }
//                            lat = it.result.latitude
//                            lon = it.result.longitude
//                            first = true
//                        } else {
//                            lat = 0.0
//                            lon = 0.0
//                        }
//                        lifecycleScope.launch {
//                            around(lat, lon)
//                            forRV()
//                            around(lat, lon)
//                        }
//                        around(lat, lon)
//                    }
//                }
//            }
//
//        } else {
//            forRV()
//            aroundNetworkDisabled()
//        }

//        lifecycleScope.launch {
//            var listToast = ArrayList<WeatherEntity>()
//            listToast.addAll(repo.getAroundCity())
//        }

//        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                onHelloClickName(query)
//                return true
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })

//        button.setOnClickListener {
//            var bc: WeatherEntity?
//            lifecycleScope.launch {
//                bc = repo.getBase()
//                if (bc != null) {
//                    if (isNetworkEnabled) onHelloClick(bc?.id)
//                    else onHelloClickNetworkDisable(bc?.id)
//                }
//                else Toast.makeText(applicationContext, "Вы ещё не выбрали ни один город", Toast.LENGTH_SHORT).show()
//            }
//        }
//        }
//    }


    private fun aroundNetworkDisabled() {
        adapter?.updateData(listAll)
        rv.adapter = CityAdapter(listAll) { id -> onHelloClickNetworkDisable(id) }
    }

    private fun forRV() {
        if (flag) rv.adapter = CityAdapter(listAll) { id -> onHelloClick(id) }
        adapter = CityAdapter(listAll) { adapter?.updateData(listAll) }
    }

//    private fun around(lat: Double, lon: Double) {
//
////            api.getSquareWeather(lat, lon).run {
////                var list: ArrayList<WeatherResponse> = ArrayList(this.list)
////                var listAll = ArrayList<WeatherEntity>()
////                for (i in 0 until list.size) {
////                    if (i == 0) listAll.clear()
////                    with(list[i]) {
////                        listAll.add(WeatherEntity(0, name, id, coord.lat, coord.lon, main.temp.toInt(), weather[0].description, main.feelsLike.toInt().toString(), wind.speed.toInt().toString(), calcWind(wind.deg)))
////                    }
////                }
////        listAll.clear()
////        listAll.addAll(presenter.around(lat, lon))
////                adapter?.updateData(listAll)
////                rv.adapter = CityAdapter(listAll) { id -> onHelloClick(id) }
//    }


    private fun onHelloClick(query: Int?) {
        var idCity: Int
        var windSpeedCity: String
        var windDeg: String
        var descr: String
        var tempCity: String
        var cityName: String
        var feel: String
        lifecycleScope.launch {
            query?.let {
                api.getWeather(it).run {
                    cityName = name
                    idCity = id
                    windSpeedCity = wind.speed.toInt().toString()
                    windDeg = calcWind(wind.deg)
                    descr = weather[0].description
                    tempCity = main.temp.toInt().toString()
                    feel = main.feelsLike.toInt().toString()
                }
                val intent = Intent(applicationContext, CheckDetailsActivity::class.java)
                intent.putExtra("ID", idCity)
                intent.putExtra("WIND_SPEED", windSpeedCity)
                intent.putExtra("DESCR", descr)
                intent.putExtra("TEMP", tempCity)
                intent.putExtra("NAME", cityName)
                intent.putExtra("WIND_DEG", windDeg)
                intent.putExtra("FEEL", feel)
                startActivity(intent)
            }
        }
    }

    private fun onHelloClickNetworkDisable(id: Int?) {
        var entity: WeatherEntity?
        lifecycleScope.launch {
            entity = presenter.onHelloClickNetworkDisable(id!!)
            val intent = Intent(applicationContext, CheckDetailsActivity::class.java)
            intent.putExtra("ID", entity?.id)
            startActivity(intent)
        }
    }

//    private fun onHelloClickName(query: String?) {
//        var idCity: Int
//        var windSpeedCity: String
//        var windDeg: String
//        var descr: String
//        var tempCity: String
//        var cityName: String
//        var feel: String
//        var entity: WeatherEntity?
//        if (isNetworkEnabled) {
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
//        } else Toast.makeText(applicationContext, "Нет подключения к Интернету", Toast.LENGTH_SHORT).show()
//    }

    private fun checkMyPermission(permissionArray: Array<out String>) : Boolean {
        success = true
        for (i in permission.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED) {
                success = false
            }
        }
        return success
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            var allowed = true
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    allowed = false
                    var requestAgain = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(permissions[i])
                    if (requestAgain) {
                        Toast.makeText(this, "PERM denied", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this, "go to settings", Toast.LENGTH_SHORT).show()
                }
            }
            if (allowed) {
                Toast.makeText(this, "PERM granted", Toast.LENGTH_SHORT).show()
            }
        }
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

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            mLastLocation = locationResult.lastLocation
        }
    }
}
