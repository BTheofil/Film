package com.example.films.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.films.model.DataSource
import com.example.films.model.Movie

class CategoryDataViewModel (application: Application) : AndroidViewModel(application) {

    private val originalData: List<Movie>

    private val categoryDataLiveData: MutableLiveData<MutableList<Movie>> = MutableLiveData()

    init {
        originalData = DataSource.createDataSet()
        categoryDataLiveData.value = originalData
    }
}