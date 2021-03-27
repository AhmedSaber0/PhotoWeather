package com.rebusta.photoweather.domain.model

import com.google.gson.annotations.SerializedName

data class MainItem(
    @field:SerializedName("temp")
    val temp: Double,

    @field:SerializedName("temp_max")
    val tempMax: Double,

    @field:SerializedName("temp_min")
    val tempMin: Double
)
