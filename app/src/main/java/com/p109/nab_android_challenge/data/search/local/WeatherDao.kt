package com.p109.nab_android_challenge.data.search.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.p109.nab_android_challenge.data.search.remote.model.Daily

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveDailyWeather(it: List<Daily?>)

    @Query("SELECT * FROM DailyWeather WHERE city = :city")
    fun getDailyWeather(city: String): LiveData<List<Daily>>

    @Query("DELETE FROM DailyWeather WHERE city = :city")
    fun deleteOldDailyWeatherWithCurrentCity(city: String)
}