package com.rebusta.photoweather.domain.usecase.weather

import com.rebusta.photoweather.domain.model.WeatherResponse

interface WeatherUseCase {

    suspend fun getWeatherData(latitude: Double, longitude: Double, apiKey: String): WeatherResponse
}