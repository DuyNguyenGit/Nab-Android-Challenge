package com.p109.nab_android_challenge.data.search.repositoryimpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.p109.nab_android_challenge.common.ContextProviders
import com.p109.nab_android_challenge.common.SingleSourceStrategy
import com.p109.nab_android_challenge.common.params.RequestParams
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.data.search.local.WeatherDao
import com.p109.nab_android_challenge.data.search.local.WeatherDb
import com.p109.nab_android_challenge.data.search.remote.WeatherService
import com.p109.nab_android_challenge.data.search.remote.model.ApiResponse
import com.p109.nab_android_challenge.data.search.remote.model.WeatherApiResponse
import com.p109.nab_android_challenge.domain.search.repository.WeatherRepository
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel
import com.p109.nab_android_challenge.common.mapper.WeatherDataMapper
import com.p109.nab_android_challenge.data.search.remote.model.Daily
import com.p109.nab_android_challenge.common.util.DateUtil
import com.p109.nab_android_challenge.common.util.toTimeMillis
import com.p109.nab_android_challenge.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class WeatherRepositoryImpl @Inject constructor(
    private val db: WeatherDb,
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
    private val coroutineContext: ContextProviders,
    private val dataModelMapper: WeatherDataMapper
) : WeatherRepository {

    override fun getWeatherByCity(city: String, requestParams: RequestParams): LiveData<ResultState<List<DailyWeatherDomainModel>>> {
        return Transformations.map(getWeather(city, requestParams)) {
            return@map ResultState(
                status = it.status,
                data = dataModelMapper.mapperToList(it.data),
                apiError = it.apiError
            )
        }
    }

    private fun getWeather(city: String, requestParams: RequestParams): LiveData<ResultState<List<Daily>>> {
        Log.d("TAG", "getWeather: >>>requestParams.appId=${requestParams.appId}")
        return object : SingleSourceStrategy<List<Daily>, WeatherApiResponse>(coroutineContext) {

            override fun saveCallResult(item: WeatherApiResponse) {
                item.list.let {
                    db.runInTransaction {
                        weatherDao.saveDailyWeather(it.map { daily: Daily ->
                            daily.apply {
                                this.city = city
                            }
                        })
                    }
                }
            }

            override fun deleteOldDailyWeatherWithCurrentCity() {
                db.runInTransaction {
                    weatherDao.deleteOldDailyWeatherWithCurrentCity(city)
                }
            }

            override fun createCall(): LiveData<ApiResponse<WeatherApiResponse>> =
                weatherService.getWeather(
                    city, requestParams.q, requestParams.units, requestParams.appId
                )

            override fun shouldFetch(data: List<Daily>?): Boolean {
                val currentTime = System.currentTimeMillis()
                val currentDate = DateUtil.convertFromTimeStamp(currentTime)
                data?.let {
                    if (data.isNotEmpty()) {
                        val firstDaily = data[0]
                        val previousDate = DateUtil.convertFromTimeStamp(firstDaily.dt.toTimeMillis())
                        return currentDate != previousDate && city == firstDaily.city
                    } else {
                        return true
                    }
                }
                return true
            }

            override fun loadFromDb(): LiveData<List<Daily>> = weatherDao.getDailyWeather(city)

        }.asLiveData()
    }
}