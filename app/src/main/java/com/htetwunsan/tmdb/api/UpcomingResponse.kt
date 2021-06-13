package com.htetwunsan.tmdb.api

import com.google.gson.annotations.SerializedName
import com.htetwunsan.tmdb.model.UpcomingMovie

data class UpcomingResponse(
    @SerializedName("total_results") val totalResults: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("results") val results: List<UpcomingMovie> = emptyList(),
    val nextPage: Int? = null
)
