package com.p109.nab_android_challenge.data.search.repositoryimpl

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.common.mapper.WeatherDataMapper
import com.p109.nab_android_challenge.common.params.RequestParams
import com.p109.nab_android_challenge.data.search.local.WeatherDao
import com.p109.nab_android_challenge.data.search.local.WeatherDb
import com.p109.nab_android_challenge.data.search.remote.WeatherService
import com.p109.nab_android_challenge.data.search.remote.model.Daily
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel
import com.p109.nab_android_challenge.util.ApiUtil
import com.p109.nab_android_challenge.util.TestContextProvider
import com.p109.nab_android_challenge.util.TestUtil
import com.p109.nab_android_challenge.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class WeatherRepositoryImplTest {

    companion object {
        const val CITY = "saigon"
        const val CNT = 7
        const val UNITS = "metric"
        const val APPID = "abc"
    }

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var weatherRepositoryImpl: WeatherRepositoryImpl
    private val db = mock<WeatherDb>()
    private val dao = mock<WeatherDao>()
    private val remoteService = mock<WeatherService>()
    private val requestParams = RequestParams(CNT, UNITS, APPID)
    private val mapper = WeatherDataMapper()

    @Before
    fun setUp() {
        whenever(db.getWeatherDao()).thenReturn(dao)
        whenever(db.runInTransaction(any())).thenCallRealMethod()
        weatherRepositoryImpl = WeatherRepositoryImpl(
            db, dao, remoteService, TestContextProvider(), mapper
        )
    }

    @Test
    fun getWeatherByCity() {
        val dbData = MutableLiveData<List<Daily>>()
        whenever(dao.getDailyWeather(any())).thenReturn(dbData)

        val weatherResponse = TestUtil.fakeWeatherResponse(
            city = CITY,
            cnt = CNT
        )
        val call = ApiUtil.successRemoteCall(weatherResponse)
        whenever(remoteService.getWeather(CITY, CNT, UNITS, APPID)).thenReturn(call)

        val data = weatherRepositoryImpl.getWeatherByCity(CITY, requestParams)
        verify(dao).getDailyWeather(CITY)
        verifyNoMoreInteractions(remoteService)

        val observer = mock<Observer<ResultState<List<DailyWeatherDomainModel>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(remoteService)
        verify(observer).onChanged(ResultState.loading(emptyList()))

        //test case fetching data from remote
        val updateDBData = MutableLiveData(listOf(TestUtil.createFakeDailyWeather(CITY)))
        whenever(dao.getDailyWeather(any())).thenReturn(updateDBData)
        dbData.postValue(null)
        verify(remoteService).getWeather(CITY, CNT, UNITS, APPID)
        verify(dao).saveDailyWeather(weatherResponse.list)
        updateDBData.postValue(weatherResponse.list)
        verify(observer).onChanged(
            ResultState.success(
                TestUtil.createFakeDailyWeatherDomainModel(
                    mapper = mapper,
                    dailyList = weatherResponse.list
                )
            )
        )
    }
}