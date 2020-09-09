package com.example.films.model

import java.io.Serializable

data class Movie(
    var title: String,
    var year: Int,
    var rateing: Int,
    var details: String,
    var image: Int
) : Serializable
