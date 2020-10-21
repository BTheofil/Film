package com.example.films.db

import androidx.room.*
import com.example.films.db.entity.FavouriteMovie

@Dao
interface FavouriteDAO {
    @Query("SELECT * FROM favourite_movies")
    fun getAll(): List<FavouriteMovie>

    @Insert
    fun addMovie(movie: FavouriteMovie)

    @Delete
    fun delete(movie: FavouriteMovie)

    @Update
    fun updateFavouriteMovies(movies: FavouriteMovie)
}