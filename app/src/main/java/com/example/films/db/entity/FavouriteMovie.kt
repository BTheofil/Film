package com.example.films.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.films.model.CategoryList
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "favourite_movies")
data class FavouriteMovie (
    @PrimaryKey (autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "image") var image: String,
    @ColumnInfo(name = "year") var year: Date,
    @ColumnInfo(name = "rate") var rate: Int,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "category") var categoryList: ArrayList<Int>
)