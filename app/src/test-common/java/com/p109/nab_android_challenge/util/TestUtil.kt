package com.p109.nab_android_challenge.util

import com.p109.nab_android_challenge.common.datastates.ApiError
import com.p109.nab_android_challenge.common.datastates.ResultState
import com.p109.nab_android_challenge.common.mapper.WeatherDataMapper
import com.p109.nab_android_challenge.data.search.remote.model.Daily
import com.p109.nab_android_challenge.data.search.remote.model.WeatherApiResponse
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel

object TestUtil {

    fun fakeWeatherResponse(city: String, cnt: Int): WeatherApiResponse = WeatherApiResponse(
        city = null, cnt = cnt, cod = "", message = 0.0, list = createListOfDailyWeather(city)
    )

    fun <T> createLoadingNullResultState(): ResultState<List<T>> = ResultState.loading(null)

    fun <T> createErrorNullResultState(): ResultState<List<T>> =
        ResultState.error(ApiError("401", "Some error message"), null)

    private fun createListOfDailyWeather(city: String): List<Daily> = listOf(
        createFakeDailyWeather(city)
    )

    fun createFakeDailyWeather(city: String): Daily =
        Daily(
            city = city,
            dt = 1000000,
            temp = null,
            clouds = 0,
            deg = 0,
            feelsLike = null,
            gust = 0.0,
            humidity = 10,
            pop = 0.0,
            pressure = 1001,
            rain = 0.0,
            speed = 0.0,
            sunrise = 0,
            sunset = 0,
            weather = listOf(
                Daily.Weather(
                    description = "example",
                    icon = "",
                    id = 0,
                    main = ""
                )
            )
        )

    fun createFakeDailyWeatherDomainModel(
        mapper: WeatherDataMapper,
        dailyList: List<Daily>
    ): List<DailyWeatherDomainModel> =
        mapper.mapperToList(dailyList)

}