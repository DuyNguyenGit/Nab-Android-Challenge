package com.p109.nab_android_challenge.domain.search.businessmodel

import com.p109.nab_android_challenge.common.basemodels.DomainDataModel

data class DailyWeatherDomainModel(
    val date: String,
    val averageTemp: Int,
    val pressure: Int,
    val humidity:Int,
    val description: String,
    val city: String
) : DomainDataModel()