package com.rebusta.photoweather.di

import com.rebusta.photoweather.app.PhotoWeatherApp
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val appModule = module {

    koinApplication { PhotoWeatherApp() }
}