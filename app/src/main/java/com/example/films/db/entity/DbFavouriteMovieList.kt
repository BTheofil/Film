package com.example.films.db.entity

import com.google.gson.annotations.SerializedName

data class DbFavouriteMovieList (
    @SerializedName("results")
    var list: List<FavouriteMovie>? = null
)