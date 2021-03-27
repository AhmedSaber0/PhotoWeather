package com.rebusta.photoweather.data.remote

import com.rebusta.photoweather.domain.model.WeatherResponse

interface WeatherRemoteDataSource {

    suspend fun getWeatherData(latitude: Double, longitude: Double, apiKey: String): WeatherResponse

}