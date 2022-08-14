package com.p109.nab_android_challenge.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.common.datastore.DataStore
import com.p109.nab_android_challenge.common.params.RequestParams
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel
import com.p109.nab_android_challenge.domain.search.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private var _edtCityState = MutableLiveData<String>()
    val edtCityState = _edtCityState
    private var _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    var city = MutableLiveData<String>()
    var result: LiveData<ResultState<List<DailyWeatherDomainModel>>> =
        Transformations.switchMap(_query) { search ->
            weatherRepository.getWeatherByCity(
                city = search,
                requestParams = RequestParams(
                    q = 7,
                    units = "metric",
                    appId = DataStore.weatherAppId
                )
            )
        }

    fun getWeather() {
        val query = city.value
        if (!query.isNullOrEmpty() && query.length >= 3) {
            this._query.value = query
            this._edtCityState.value = null
        } else {
            this._edtCityState.value = query
        }
    }

}