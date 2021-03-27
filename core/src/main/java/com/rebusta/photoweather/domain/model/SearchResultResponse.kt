package com.rebusta.photoweather.domain.model

import com.google.gson.annotations.SerializedName

data class SearchResultResponse(
    @field:SerializedName("items")
    val repositories: List<RepositoryItem>,
    @field:SerializedName("total_count")
    val total_count: Int
)