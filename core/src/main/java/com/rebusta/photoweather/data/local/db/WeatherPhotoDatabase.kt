package com.rebusta.photoweather.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rebusta.photoweather.data.local.dao.PhotosDao
import com.rebusta.photoweather.data.local.entity.WeatherPhoto


@Database(entities = [WeatherPhoto::class],version = 1,exportSchema = false)
abstract class WeatherPhotoDatabase : RoomDatabase(){

    abstract val photosDao : PhotosDao
}