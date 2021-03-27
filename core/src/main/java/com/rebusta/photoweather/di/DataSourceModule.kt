package com.rebusta.photoweather.di

import com.rebusta.photoweather.data.local.WeatherPhotoLocalDataSource
import com.rebusta.photoweather.data.local.WeatherPhotoLocalDataSourceImpl
import com.rebusta.photoweather.data.remote.WeatherRemoteDataSource
import com.rebusta.photoweather.data.remote.WeatherRemoteDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    factory<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(apiService = get()) }
    factory<WeatherPhotoLocalDataSource> { WeatherPhotoLocalDataSourceImpl(photosDao = get()) }
}