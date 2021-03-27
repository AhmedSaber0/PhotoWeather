package com.rebusta.photoweather.domain.usecase.weather

import com.rebusta.photoweather.data.repo.WeatherRepository
import com.rebusta.photoweather.domain.model.WeatherResponse

class WeatherUseCaseImpl(private val weatherRepository: WeatherRepository) : WeatherUseCase {

    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse =
        weatherRepository.getWeatherData(latitude = latitude, longitude = longitude, apiKey = apiKey)
}