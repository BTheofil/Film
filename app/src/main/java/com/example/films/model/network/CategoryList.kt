package com.example.films.model.network

import com.example.films.model.network.Category
import com.google.gson.annotations.SerializedName

data class CategoryList (
    @SerializedName("genres")
    var list: List<Category>? = null
)
