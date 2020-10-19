package com.example.films.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_movies")
data class FavouriteMovie (
    @PrimaryKey (autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "year") var year: Int,
    @ColumnInfo(name = "rate") var rate: String,
    @ColumnInfo(name = "description") var description: String

)