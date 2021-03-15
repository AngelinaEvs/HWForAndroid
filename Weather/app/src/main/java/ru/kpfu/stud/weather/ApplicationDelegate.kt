package ru.kpfu.stud.weather

import android.app.Application
import moxy.MvpFacade

class ApplicationDelegate: Application() {

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
    }
}
