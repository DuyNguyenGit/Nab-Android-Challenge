package com.p109.nab_android_challenge.data.search.local

import androidx.room.TypeConverter
import com.p109.nab_android_challenge.data.search.remote.model.Daily
import com.google.gson.Gson

class FeelLikesConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToFeelLikes(data: String?): Daily.FeelsLike? = try {
        gson.fromJson(data, Daily.FeelsLike::class.java)
    } catch (_: Exception) {
        null
    }

    @TypeConverter
    fun feelLikesToString(feelsLike: Daily.FeelsLike?): String? {
        return gson.toJson(feelsLike)
    }
}