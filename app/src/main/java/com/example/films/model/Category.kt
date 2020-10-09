package com.example.films.model

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)
