package com.p109.nab_android_challenge.common.di

import android.content.Context
import com.p109.nab_android_challenge.BuildConfig
import com.p109.nab_android_challenge.common.ContextProviders
import com.p109.nab_android_challenge.common.networks.LiveDataCallAdapterFactory
import com.p109.nab_android_challenge.data.search.local.WeatherDb
import com.p109.nab_android_challenge.data.search.remote.WeatherService
import com.p109.nab_android_challenge.common.mapper.WeatherDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .callTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .callTimeout(40, TimeUnit.SECONDS)
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(40, TimeUnit.SECONDS)
        .writeTimeout(40, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WeatherService.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        WeatherDb.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDb) = db.getWeatherDao()

    @Singleton
    @Provides
    fun providesContextProvider() = ContextProviders.getInstance()

    @Singleton
    @Provides
    fun provideWeatherDataModelMapper() = WeatherDataMapper()

}