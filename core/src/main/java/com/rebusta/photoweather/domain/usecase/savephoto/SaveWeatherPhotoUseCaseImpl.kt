package com.rebusta.photoweather.domain.usecase.savephoto

import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.data.repo.WeatherRepository
import com.rebusta.photoweather.domain.model.WeatherResponse

class SaveWeatherPhotoUseCaseImpl(private val weatherRepository: WeatherRepository) : SaveWeatherPhotoUseCase {

    override suspend fun saveWeatherPhoto(weatherPhoto: WeatherPhoto)  = weatherRepository.saveWeatherPhoto(weatherPhoto)
}