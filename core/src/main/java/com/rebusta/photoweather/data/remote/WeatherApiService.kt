package com.rebusta.photoweather.data.remote

import com.rebusta.photoweather.domain.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double, @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") unit: String? = "metric"
    ): WeatherResponse

}