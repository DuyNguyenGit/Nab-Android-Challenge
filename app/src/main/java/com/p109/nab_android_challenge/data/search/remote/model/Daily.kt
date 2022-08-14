package com.p109.nab_android_challenge.data.search.remote.model

import androidx.room.*
import com.p109.nab_android_challenge.common.basemodels.DataModel
import com.p109.nab_android_challenge.data.search.local.FeelLikesConverter
import com.p109.nab_android_challenge.data.search.local.TempTypeConverter
import com.p109.nab_android_challenge.data.search.local.WeatherTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "DailyWeather", primaryKeys = ["dt", "city"])
@TypeConverters(TempTypeConverter::class, WeatherTypeConverter::class, FeelLikesConverter::class)
data class Daily(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("deg")
    val deg: Int,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike?,
    @SerializedName("gust")
    val gust: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("speed")
    val speed: Double,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("temp")
    val temp: Temp?,
    @SerializedName("weather")
    val weather: List<Weather>,
    @ColumnInfo(name="city") var city: String
) : DataModel() {
    data class FeelsLike(
        @SerializedName("day")
        val day: Double,
        @SerializedName("eve")
        val eve: Double,
        @SerializedName("morn")
        val morn: Double,
        @SerializedName("night")
        val night: Double
    )

    data class Temp(
        @SerializedName("day")
        val day: Double,
        @SerializedName("eve")
        val eve: Double,
        @SerializedName("max")
        val max: Double,
        @SerializedName("min")
        val min: Double,
        @SerializedName("morn")
        val morn: Double,
        @SerializedName("night")
        val night: Double
    )

    data class Weather(
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String
    )
}