package com.p109.nab_android_challenge

import android.app.Application
import com.p109.nab_android_challenge.common.datastore.DataStore
import com.p109.nab_android_challenge.common.datastore.SharedPref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(applicationContext)
        val weatherAppId = getString(R.string.weather_app_id)
        DataStore.saveWeatherAppId(weatherAppId)
    }
}