package com.example.films.api.model

import com.example.films.model.Category
import com.google.gson.annotations.SerializedName

data class ApiCategoryList (
    @SerializedName("genres")
    var list: List<ApiCategory>? = null
)
