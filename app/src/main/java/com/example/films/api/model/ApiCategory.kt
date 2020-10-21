package com.example.films.api.model

import com.google.gson.annotations.SerializedName

data class ApiCategory (
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
)
