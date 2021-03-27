package com.rebusta.photoweather.domain.usecase.getphotos

import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.domain.model.WeatherResponse

interface GetWeatherPhotosUseCase {

    suspend fun getWeatherPhotoList() : List<WeatherPhoto>
}