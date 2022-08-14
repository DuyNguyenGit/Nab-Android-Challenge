package com.p109.nab_android_challenge.data.search.remote

import androidx.lifecycle.LiveData
import com.p109.nab_android_challenge.data.search.remote.model.WeatherApiResponse
import com.p109.nab_android_challenge.data.search.remote.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/"
    }

    @GET("data/2.5/forecast/daily")
    fun getWeather(
        @Query("q") query: String,
        @Query("cnt") cnt: Int,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): LiveData<ApiResponse<WeatherApiResponse>>

}