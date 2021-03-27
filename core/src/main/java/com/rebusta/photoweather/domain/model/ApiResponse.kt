package com.rebusta.photoweather.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("status")
    @Expose
    var success: Boolean,
    @SerializedName("code")
    @Expose
    var code: String,
    @SerializedName("message")
    @Expose
    var message: String,
    @SerializedName("data")
    @Expose
    var data: T? = null
) {
    override fun toString(): String {
        return "ApiResponse(success=$success, message=$message, data=$data)"
    }
}