package com.example.films.model.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavouriteMovie::class), version = 1)
abstract class FavouriteMovieDataBase: RoomDatabase() {

    abstract fun favouriteMovieDAO(): FavouriteMovie
}