package ru.kpfu.stud.weather.presentation.activity

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
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.stud.weather.data.LocationRepositoryImpl

import ru.kpfu.stud.weather.utils.getErrorMessage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import ru.kpfu.stud.weather.R
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.kpfu.stud.weather.data.WeatherRepositoryImpl
import ru.kpfu.stud.weather.data.api.ApiFactory
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity
import ru.kpfu.stud.weather.domain.FindCityUseCase
import ru.kpfu.stud.weather.presentation.rv.CityAdapter


class SearchActivity : MvpAppCompatActivity(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun presenter(): MainPresenter = initPresenter()

    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private var permission: Array<out String> = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val PERMISSION_REQUEST_CODE = 1001
    var listAll = ArrayList<WeatherEntity>()
    var adapter : CityAdapter? = null
    var success: Boolean = true
    var isNetworkEnabled = false

    lateinit var mLastLocation: Location
    private lateinit var mLocationRequest: LocationRequest
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    var first = false

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
                }
            }
        }
        button.setOnClickListener { presenter.onButtonClick() }
    }

    private fun initPresenter() = MainPresenter(
        findCityUseCase = FindCityUseCase(WeatherRepositoryImpl(ApiFactory.weatherApi, applicationContext),
            Dispatchers.IO),
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

    override fun showDetails(entity: WeatherEntity) {
        val intent = Intent(applicationContext, CheckDetailsActivity::class.java)
        with (entity) {
            intent.putExtra("ID", id)
            intent.putExtra("WIND_SPEED", speed)
            intent.putExtra("DESCR", description)
            intent.putExtra("TEMP", temp)
            intent.putExtra("NAME", name)
            intent.putExtra("WIND_DEG", direction)
            intent.putExtra("FEEL", feel)
        }
        startActivity(intent)
    }

    override fun checkLocationPermission(): Boolean {
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

    override fun showWeather(list: List<WeatherEntity>) {
        listAll.clear()
        listAll.addAll(list)
        adapter?.updateData(listAll)
        rv.adapter = CityAdapter(listAll) { id -> presenter.onHelloClickId(id) }
    }

    override fun showUserLocation(location: Location) {
        presenter.onLocationClick()
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

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            mLastLocation = locationResult.lastLocation
        }
    }
}
