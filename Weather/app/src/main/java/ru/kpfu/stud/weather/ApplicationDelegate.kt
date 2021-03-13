package com.itis.template

import android.app.Application
import moxy.MvpFacade

class ApplicationDelegate: Application() {

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
    }
}
