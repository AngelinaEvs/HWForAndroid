package ru.kpfu.stud.weather.data

import android.annotation.SuppressLint
import android.app.Activity
import com.google.android.gms.location.LocationServices

class MyLocation {

    @SuppressLint("MissingPermission")
    fun getLocation(activity: Activity): Pair<Double, Double> {
        var lat: Double = 30.0
        var lon: Double = 30.0
        LocationServices.getFusedLocationProviderClient(activity).lastLocation.addOnCompleteListener {
            if (it != null) {
                while (!it.isComplete) {

                }
                lat = it.result.latitude
                lon = it.result.longitude
            } else {
                lat = 0.0
                lon = 0.0
            }
        }
        return Pair(lat, lon)
    }
}