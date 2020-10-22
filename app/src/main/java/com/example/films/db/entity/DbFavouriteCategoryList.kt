package com.example.films.db.entity

import com.example.films.model.Category
import com.google.gson.annotations.SerializedName

data class DbFavouriteCategoryList(
    @SerializedName("genres")
    var list: ArrayList<DbFavouriteCategory>? = null
)