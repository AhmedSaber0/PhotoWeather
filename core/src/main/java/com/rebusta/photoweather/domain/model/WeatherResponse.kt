package com.rebusta.photoweather.domain.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

	@field:SerializedName("id")
	val locationId: String,

	@field:SerializedName("main")
	val mainItem: MainItem,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("sys")
	val sys: SysItem,

	@field:SerializedName("weather")
	val weather: List<WeatherItem>,
)
