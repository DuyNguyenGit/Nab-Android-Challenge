package com.p109.nab_android_challenge.ui.search.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.common.datastore.DataStore
import com.p109.nab_android_challenge.common.params.RequestParams
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel
import com.p109.nab_android_challenge.domain.search.repository.WeatherRepository
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    companion object {
        const val DATE = "Tue, 10 Mar 2022"
        const val AVERAGE_TEMP = 28
        const val HUMIDITY = 10
        const val PRESSURE = 1000
        const val CITY = "saigon"
        const val DESC = "lighrain"
    }

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()
    private val repository = mock<WeatherRepository>()
    private lateinit var viewModel: SearchViewModel
    private val requestParams = RequestParams(7, "metric", "abc")
    private val dailyWeatherDomainModel = DailyWeatherDomainModel(
        date = DATE,
        averageTemp = AVERAGE_TEMP,
        humidity = HUMIDITY,
        pressure = PRESSURE,
        city = CITY,
        description = DESC
    )
    private val result = MutableLiveData<ResultState<List<DailyWeatherDomainModel>>>(
        ResultState.success(data = listOf(dailyWeatherDomainModel))
    )

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun basic() {
        val observeResult =
            com.p109.nab_android_challenge.util.mock<Observer<ResultState<List<DailyWeatherDomainModel>>>>()
        viewModel.result.observeForever(observeResult)
        mockkObject(DataStore)
        every { DataStore.weatherAppId } returns "abc"
        whenever(repository.getWeatherByCity(any(), any())).thenReturn(result)
        viewModel.city.value = "saigon"
        viewModel.getWeather()
        Mockito.verify(repository).getWeatherByCity("saigon", requestParams)
    }

    @Test
    fun setCity() {
//        viewModel.city.observeForever("saigon")
//        viewModel.city.value = "saigon"

    }

    @Test
    fun getResult() {
    }

    @Test
    fun setResult() {
    }

    @Test
    fun getWeather() {
    }
}