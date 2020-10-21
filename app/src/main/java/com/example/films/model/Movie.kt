package com.example.films.model

import java.io.Serializable
import java.util.*

data class Movie(
    var title: String,
    var releaseDate: Date,
    var rating: Int,
    var details: String,
    var image: String?,
    var categoryArrayList: ArrayList<Int>
) : Serializable{
    val year:Int
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = releaseDate
        return calendar.get(Calendar.YEAR)
    }
}
