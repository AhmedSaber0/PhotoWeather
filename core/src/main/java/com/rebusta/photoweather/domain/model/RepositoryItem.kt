package com.rebusta.photoweather.domain.model

import com.google.gson.annotations.SerializedName

data class RepositoryItem(
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("owner")
    val owner: Owner?
) {
    data class Owner(
        @field:SerializedName("avatar_url")
        val avatarUrl: String
    )
}
