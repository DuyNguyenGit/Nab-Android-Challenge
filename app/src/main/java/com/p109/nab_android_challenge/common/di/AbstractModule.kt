package com.p109.nab_android_challenge.common.di

import com.p109.nab_android_challenge.data.search.repositoryimpl.WeatherRepositoryImpl
import com.p109.nab_android_challenge.domain.search.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractModule {

    @Binds
    abstract fun provideWeatherRepository(repositoryImpl: WeatherRepositoryImpl): WeatherRepository

}