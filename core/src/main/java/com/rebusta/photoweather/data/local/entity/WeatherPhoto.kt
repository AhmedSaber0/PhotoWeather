package com.rebusta.photoweather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherPhoto")
data class WeatherPhoto(
    @PrimaryKey
    val photoPath: String,
    val name: String
)
