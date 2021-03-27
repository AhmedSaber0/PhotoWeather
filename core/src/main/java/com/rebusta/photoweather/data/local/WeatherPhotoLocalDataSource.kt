package com.rebusta.photoweather.data.local

import com.rebusta.photoweather.data.local.entity.WeatherPhoto

interface WeatherPhotoLocalDataSource {

    suspend fun saveWeatherPhoto(weatherPhoto: WeatherPhoto)

    suspend fun getWeatherPhotoList() : List<WeatherPhoto>

}