package com.rebusta.photoweather.domain.usecase.savephoto

import com.rebusta.photoweather.data.local.entity.WeatherPhoto

interface SaveWeatherPhotoUseCase {

    suspend fun saveWeatherPhoto(weatherPhoto: WeatherPhoto)
}