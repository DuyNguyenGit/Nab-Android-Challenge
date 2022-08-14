package com.p109.nab_android_challenge.util

import androidx.lifecycle.MutableLiveData
import com.p109.nab_android_challenge.data.search.remote.model.ApiResponse
import com.p109.nab_android_challenge.data.search.remote.model.WeatherApiResponse
import retrofit2.Response

object ApiUtil {
    fun successRemoteCall(data: WeatherApiResponse) = createWeatherCall(Response.success(data))

    private fun createWeatherCall(response: Response<WeatherApiResponse>) =
        MutableLiveData<ApiResponse<WeatherApiResponse>>().apply {
            value = ApiResponse.create(response)
        }
}