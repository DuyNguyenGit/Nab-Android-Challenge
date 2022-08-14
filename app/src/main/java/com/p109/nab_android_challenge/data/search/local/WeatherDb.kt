package com.p109.nab_android_challenge.data.search.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.p109.nab_android_challenge.data.search.remote.model.Daily

@Database(
    entities = [Daily::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao


    companion object {
        private const val DB_NAME = "dailyweather"

        @Volatile
        private var instance: WeatherDb? = null

        fun getDatabase(context: Context): WeatherDb =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, WeatherDb::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}