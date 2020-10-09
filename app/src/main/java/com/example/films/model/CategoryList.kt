package com.example.films.model

import com.google.gson.annotations.SerializedName

data class CategoryList (
    @SerializedName("genres")
    var list: List<Category>? = null
)
