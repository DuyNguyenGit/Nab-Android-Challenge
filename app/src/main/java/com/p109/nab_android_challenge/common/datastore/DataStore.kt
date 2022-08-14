package com.p109.nab_android_challenge.common.datastore

object DataStore {
    private val WEATHER_APPID_KEY = "app_id"

    var weatherAppId: String
        get() = SharedPref.read(WEATHER_APPID_KEY, "")
        set(value) = SharedPref.write(WEATHER_APPID_KEY, value)

    fun saveWeatherAppId(appId: String) {
        weatherAppId = appId
    }

}