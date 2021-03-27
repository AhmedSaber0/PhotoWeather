package com.rebusta.photoweather.di

import com.rebusta.photoweather.data.repo.WeatherRepository
import com.rebusta.photoweather.data.repo.WeatherRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<WeatherRepository> { WeatherRepositoryImpl(remoteDataSource = get(), localDataSource = get()) }
}