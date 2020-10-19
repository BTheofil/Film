package com.example.films.model.entity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteMovieViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<FavouriteMovie>>
    private val repository: FavouriteMovieRepository

    init {
        val FMDao = FavouriteMovieDataBase.getDataBase(application).favouriteMovieDAO()
        repository = FavouriteMovieRepository(FMDao)
        readAllData = repository.readAllData
    }

    fun addMovie(favouriteMovie: FavouriteMovie){
        viewModelScope.launch(Dispatchers.IO){
            repository.addMovie(favouriteMovie)
        }
    }
}