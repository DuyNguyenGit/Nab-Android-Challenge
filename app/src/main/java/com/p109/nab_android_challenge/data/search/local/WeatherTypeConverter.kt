package com.p109.nab_android_challenge.data.search.local

import androidx.room.TypeConverter
import com.p109.nab_android_challenge.data.search.remote.model.Daily
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun stringToWeatherList(data: String?): List<Daily.Weather?>? {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Daily.Weather?>?>() {}.type
        return try {
            gson.fromJson<List<Daily.Weather?>>(data, listType)
        } catch (_: Exception) {
            null
        }
    }

    @TypeConverter
    fun weatherListToString(weatherList: List<Daily.Weather?>?): String? {
        return gson.toJson(weatherList)
    }
}