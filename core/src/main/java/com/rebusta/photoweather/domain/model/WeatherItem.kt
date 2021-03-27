package com.rebusta.photoweather.domain.model

import com.google.gson.annotations.SerializedName

data class WeatherItem(
    @field:SerializedName("icon")
    val icon: String,

    @field:SerializedName("main")
    val main: String,
) {
    fun getIconUrl(): String {
        return "http://openweathermap.org/img/wn/$icon@2x.png"
    }

}
