package com.rebusta.photoweather.data.repo

import com.rebusta.photoweather.data.local.WeatherPhotoLocalDataSource
import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.data.remote.WeatherRemoteDataSource
import com.rebusta.photoweather.domain.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherPhotoLocalDataSource
) : WeatherRepository {

    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double,
        apiKey: String
    ): WeatherResponse =
        remoteDataSource.getWeatherData(latitude = latitude, longitude = longitude, apiKey = apiKey)

    override suspend fun saveWeatherPhoto(weatherPhoto: WeatherPhoto) =
        localDataSource.saveWeatherPhoto(weatherPhoto)

    override suspend fun getWeatherPhotoList(): List<WeatherPhoto> =
        localDataSource.getWeatherPhotoList()
}