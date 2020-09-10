package com.example.films.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.films.model.DataSource
import com.example.films.model.Movie

class MovieDataViewModel(application: Application) : AndroidViewModel(application) {

    val movieDataLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

    init {
        movieDataLiveData.value = DataSource.createDataSet()
    }

    fun filter() {

    }
}