package com.example.films.api.model

import com.google.gson.annotations.SerializedName

data class ApiMovieList (
    @SerializedName("results")
    var list: List<ApiMovie>? = null
)
