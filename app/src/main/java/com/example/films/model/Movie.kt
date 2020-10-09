package com.example.films.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class Movie(
    @SerializedName("title")
    var title: String,
    @SerializedName("release_date")
    var releaseDate: Date,
    @SerializedName("vote_count")
    var rating: Int,
    @SerializedName("overview")
    var details: String,
    @SerializedName("poster_path")
    var image: String?,
    @SerializedName("genre_ids")
    var categoryArrayList: ArrayList<Int>
) : Serializable{
    val year:Int
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = releaseDate
        return calendar.get(Calendar.YEAR)
    }
}
