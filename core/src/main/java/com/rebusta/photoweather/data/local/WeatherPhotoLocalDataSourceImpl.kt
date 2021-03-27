package com.rebusta.photoweather.data.local

import com.rebusta.photoweather.data.local.dao.PhotosDao
import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherPhotoLocalDataSourceImpl(private val photosDao: PhotosDao) :
    WeatherPhotoLocalDataSource {

    override suspend fun saveWeatherPhoto(weatherPhoto: WeatherPhoto) =
        withContext(Dispatchers.IO) {
            photosDao.saveWeatherPhoto(weatherPhoto)
        }

    override suspend fun getWeatherPhotoList(): List<WeatherPhoto> =
        withContext(Dispatchers.IO) {
            photosDao.getWeatherPhotoList()
        }
}