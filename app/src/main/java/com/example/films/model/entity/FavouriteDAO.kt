package com.example.films.model.entity

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteDAO {
    @Query("SELECT * FROM favourite_movies")
    fun getAll(): List<FavouriteMovie>

    @Query("SELECT * FROM favourite_movies WHERE title LIKE :title")
    fun findByTitle(title: String): LiveData<List<FavouriteMovie>>

    @Insert
    fun insertAll(vararg movie: FavouriteMovie)

    @Delete
    fun delete(movie: FavouriteMovie)

    @Update
    fun updateTodo(vararg movies: FavouriteMovie)
}