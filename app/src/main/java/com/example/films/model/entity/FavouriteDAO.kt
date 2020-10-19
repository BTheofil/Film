package com.example.films.model.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteDAO {
    @Query("SELECT * FROM favourite_movies")
    fun getAll(): LiveData<List<FavouriteMovie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: FavouriteMovie)

    @Delete
    fun delete(movie: FavouriteMovie)

    @Update
    fun updateFavouriteMovies(vararg movies: FavouriteMovie)
}