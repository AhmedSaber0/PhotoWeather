package com.rebusta.photoweather.di

import com.rebusta.photoweather.domain.usecase.getphotos.GetWeatherPhotosUseCase
import com.rebusta.photoweather.domain.usecase.getphotos.GetWeatherPhotosUseCaseImpl
import com.rebusta.photoweather.domain.usecase.savephoto.SaveWeatherPhotoUseCase
import com.rebusta.photoweather.domain.usecase.savephoto.SaveWeatherPhotoUseCaseImpl
import com.rebusta.photoweather.domain.usecase.weather.WeatherUseCase
import com.rebusta.photoweather.domain.usecase.weather.WeatherUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<WeatherUseCase> { WeatherUseCaseImpl(weatherRepository = get()) }
    factory<GetWeatherPhotosUseCase> { GetWeatherPhotosUseCaseImpl(weatherRepository = get()) }
    factory<SaveWeatherPhotoUseCase> { SaveWeatherPhotoUseCaseImpl(weatherRepository = get()) }
}