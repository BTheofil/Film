package com.example.films.model

import java.io.Serializable

data class Movie(
    var title: String,
    var year: Int,
    var rating: Int,
    var details: String,
    var image: Int,
    var categoryArrayList: ArrayList<Category>
) : Serializable

enum class Category {

    HORROR,
    SCI_FI,
    ANIMATED,
    COMEDY,
    FEMINIST,
    HUMOR,
    FAMILY

}
