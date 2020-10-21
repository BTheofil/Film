package com.example.films.db

import android.content.Context

object DB {
    private var instance: FavouriteMovieDataBase? = null

    fun getInstance(context: Context) : FavouriteMovieDataBase
    {
        if (instance == null) {
            instance = FavouriteMovieDataBase.buildDatabase(context)
        }
        return instance!!
    }
}