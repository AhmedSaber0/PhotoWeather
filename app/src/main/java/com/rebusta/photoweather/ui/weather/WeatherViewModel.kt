package com.rebusta.photoweather.ui.weather

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.rebusta.photoweather.BuildConfig
import com.rebusta.photoweather.base.BaseViewModel
import com.rebusta.photoweather.common.SingleLiveEvent
import com.rebusta.photoweather.common.viewstate.Resource
import com.rebusta.photoweather.common.viewstate.Status
import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.domain.model.WeatherResponse
import com.rebusta.photoweather.domain.usecase.savephoto.SaveWeatherPhotoUseCase
import com.rebusta.photoweather.domain.usecase.weather.WeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherUseCase: WeatherUseCase,
    private val saveWeatherPhotoUseCase: SaveWeatherPhotoUseCase
) : BaseViewModel() {

    val weatherDataResponse = SingleLiveEvent<Resource<WeatherResponse>>()

    fun getWeatherData(latitude: Double, longitude: Double) = viewModelScope.launch {
        weatherDataResponse.postValue(Resource(Status.LOADING, null, null))
        try {
            weatherUseCase.getWeatherData(
                latitude = latitude,
                longitude = longitude,
                apiKey = BuildConfig.API_KEY
            ).let {
                weatherDataResponse.postValue(Resource(Status.SUCCESS, it, null))
            }
        } catch (e: Exception) {
            weatherDataResponse.postValue(Resource(Status.NETWORK_ERROR, null, null))
        }
    }

    fun saveWeatherPhoto(photoPath: String) = viewModelScope.launch {
        try {
            saveWeatherPhotoUseCase.saveWeatherPhoto(
                weatherPhoto = WeatherPhoto(
                    photoPath = photoPath,
                    name = getPhotoName(photoPath)
                )
            )
        } catch (e: Exception) {
            Log.w("save_photo_error", e.message.toString())
        }
    }

    private fun getPhotoName(photoPath: String): String {
        val name: String
        val arr = photoPath.split("_").toTypedArray()
        name = arr[1] + "_" + arr[2]
        return name
    }
}
