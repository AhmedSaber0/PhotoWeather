package com.rebusta.photoweather.domain.model

import com.google.gson.annotations.SerializedName

data class SysItem(
    @field:SerializedName("country")
    val country: String
)
