package com.rebusta.photoweather.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rebusta.photoweather.base.BaseViewModel
import com.rebusta.photoweather.common.SingleLiveEvent
import com.rebusta.photoweather.common.viewstate.Resource
import com.rebusta.photoweather.common.viewstate.Status
import com.rebusta.photoweather.data.local.entity.WeatherPhoto
import com.rebusta.photoweather.domain.model.WeatherResponse
import com.rebusta.photoweather.domain.usecase.getphotos.GetWeatherPhotosUseCase
import com.rebusta.photoweather.domain.usecase.weather.WeatherUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val getWeatherPhotosUseCase: GetWeatherPhotosUseCase) : BaseViewModel() {

    val weatherPhotosResponse = MutableLiveData<Resource<List<WeatherPhoto>>>()

    fun getWeatherPhotoList() = viewModelScope.launch {
        weatherPhotosResponse.postValue(Resource(Status.LOADING, null, null))
        try {
            getWeatherPhotosUseCase.getWeatherPhotoList().let {
                weatherPhotosResponse.postValue(Resource(Status.SUCCESS, it, null))
            }
        } catch (e: Exception) {
            weatherPhotosResponse.postValue(Resource(Status.NETWORK_ERROR, null, null))
        }
    }

}
