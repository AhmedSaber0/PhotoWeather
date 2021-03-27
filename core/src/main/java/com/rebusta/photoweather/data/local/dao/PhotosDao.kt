package com.rebusta.photoweather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rebusta.photoweather.data.local.entity.WeatherPhoto

@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeatherPhoto(weatherPhoto: WeatherPhoto)

    @Query("SELECT * FROM weatherPhoto")
    suspend fun getWeatherPhotoList(): List<WeatherPhoto>
}