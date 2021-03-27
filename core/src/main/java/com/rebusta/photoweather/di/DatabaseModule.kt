package com.rebusta.photoweather.di

import androidx.room.Room
import com.rebusta.photoweather.common.WEATHER_PHOTO_DB
import com.rebusta.photoweather.data.local.db.WeatherPhotoDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), WeatherPhotoDatabase::class.java, WEATHER_PHOTO_DB)
            .fallbackToDestructiveMigration().build()
    }

    factory { get<WeatherPhotoDatabase>().photosDao }
}