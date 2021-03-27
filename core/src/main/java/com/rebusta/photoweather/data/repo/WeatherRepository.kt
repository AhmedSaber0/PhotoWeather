package com.rebusta.photoweather.data.repo

import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.domain.model.WeatherResponse

interface WeatherRepository {

    suspend fun getWeatherData(latitude: Double, longitude: Double, apiKey: String): WeatherResponse

    suspend fun saveWeatherPhoto(weatherPhoto: WeatherPhoto)

    suspend fun getWeatherPhotoList() : List<WeatherPhoto>

}