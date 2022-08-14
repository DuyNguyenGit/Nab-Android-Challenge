package com.p109.nab_android_challenge.domain.search.repository

import androidx.lifecycle.LiveData
import com.p109.nab_android_challenge.common.params.RequestParams
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel
import com.p109.nab_android_challenge.testing.OpenForTesting

@OpenForTesting
interface WeatherRepository {
    fun getWeatherByCity(
        city: String,
        requestParams: RequestParams
    ): LiveData<ResultState<List<DailyWeatherDomainModel>>>
}