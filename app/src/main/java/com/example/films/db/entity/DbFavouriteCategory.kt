package com.example.films.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_category")
data class DbFavouriteCategory (
    @PrimaryKey(autoGenerate = true)
    var key: Int,

    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String

)