package com.rebusta.photoweather.domain.usecase.getphotos

import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.data.repo.WeatherRepository

class GetWeatherPhotosUseCaseImpl(private val weatherRepository: WeatherRepository) :
    GetWeatherPhotosUseCase {

    override suspend fun getWeatherPhotoList(): List<WeatherPhoto> =
        weatherRepository.getWeatherPhotoList()
}