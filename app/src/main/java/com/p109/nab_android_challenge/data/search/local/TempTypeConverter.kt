package com.p109.nab_android_challenge.data.search.local

import androidx.room.TypeConverter
import com.p109.nab_android_challenge.data.search.remote.model.Daily
import com.google.gson.Gson

class TempTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToTemp(data: String?): Daily.Temp? = try {
        gson.fromJson(data, Daily.Temp::class.java)
    } catch (_: Exception) {
        null
    }

    @TypeConverter
    fun tempToString(temp: Daily.Temp?): String? {
        return gson.toJson(temp)
    }
}
