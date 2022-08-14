package com.p109.nab_android_challenge.common.mapper

import com.p109.nab_android_challenge.data.search.remote.model.Daily
import com.p109.nab_android_challenge.domain.search.businessmodel.DailyWeatherDomainModel
import com.p109.nab_android_challenge.common.util.DateUtil
import com.p109.nab_android_challenge.common.util.toTimeMillis
import kotlin.math.roundToInt

class WeatherDataMapper : ModelMapper<Daily, DailyWeatherDomainModel> {
    override fun mapperToViewDataModel(dataModel: Daily): DailyWeatherDomainModel {
        return DailyWeatherDomainModel(
            date = DateUtil.convertFromTimeStamp(dataModel.dt.toTimeMillis()),
            averageTemp = dataModel.temp?.day?.roundToInt()?: 0,
            pressure = dataModel.pressure,
            humidity = dataModel.humidity,
            description = if (dataModel.weather.isNotEmpty()) dataModel.weather[0].description else "",
            city = dataModel.city
        )
    }

    fun mapperToList(dataModelList: List<Daily>?): List<DailyWeatherDomainModel> {
        val viewDataModelList = arrayListOf<DailyWeatherDomainModel>()
        return viewDataModelList.apply {
            dataModelList?.forEach { viewDataModelList.add(mapperToViewDataModel(it)) }
        }
    }
}