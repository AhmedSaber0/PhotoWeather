package com.rebusta.photoweather.di

import com.rebusta.photoweather.ui.home.HomeViewModel
import com.rebusta.photoweather.ui.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(getWeatherPhotosUseCase = get()) }
    viewModel { WeatherViewModel(weatherUseCase = get(), saveWeatherPhotoUseCase = get()) }
}