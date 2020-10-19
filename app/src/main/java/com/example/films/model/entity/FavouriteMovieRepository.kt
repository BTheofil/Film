package com.example.films.model.entity

import androidx.lifecycle.LiveData

class FavouriteMovieRepository(private val FMDDao: FavouriteDAO) {

    val readAllData: LiveData<List<FavouriteMovie>> = FMDDao.getAll()

    suspend fun addMovie(favouriteMovie: FavouriteMovie){
        FMDDao.addMovie(favouriteMovie)
    }
}