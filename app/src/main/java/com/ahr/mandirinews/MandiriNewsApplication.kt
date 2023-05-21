package com.ahr.mandirinews

import android.app.Application
import com.blongho.country_data.World
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MandiriNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        World.init(this)
    }
}