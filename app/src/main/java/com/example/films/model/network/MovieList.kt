package com.example.films.model.network

import com.google.gson.annotations.SerializedName

data class MovieList (
    @SerializedName("results")
    var list: List<Movie>? = null
)
