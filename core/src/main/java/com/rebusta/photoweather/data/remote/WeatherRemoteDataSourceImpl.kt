package com.rebusta.photoweather.data.remote

import com.rebusta.photoweather.domain.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRemoteDataSourceImpl(private val apiService: WeatherApiService) :
    WeatherRemoteDataSource {

    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse =
        withContext(Dispatchers.IO) {
            apiService.getWeatherData(latitude = latitude, longitude = longitude, apiKey = apiKey)
        }
}