package com.example.films.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.films.model.DataSource
import com.example.films.model.Movie
import com.example.films.view.MovieRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.Executors

class MovieDataViewModel(application: Application) : AndroidViewModel(application) {

    private val originalData: List<Movie>

    val movieDataLiveData: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    val selectedMovieLiveData: MutableLiveData<Movie> = MutableLiveData()

    init {
        originalData = DataSource.createDataSet()
        movieDataLiveData.value = originalData
    }

    fun search(pattern: String?) {
        Executors.newSingleThreadExecutor().execute {
            val r = filter(pattern)
            movieDataLiveData.postValue(r)
        }
    }

    fun selectMovie(movie: Movie){
        selectedMovieLiveData.value = movie
    }

    private fun filter(pattern: String?): MutableList<Movie> {
            val filteredData = mutableListOf<Movie>()

            if (pattern != null && pattern.isNotEmpty()) {
                filteredData.clear()
                val search = pattern.toLowerCase(Locale.getDefault())
                originalData.forEach {
                    if (it.title.toLowerCase(Locale.getDefault()).contains(search)) {
                        filteredData.add(it)
                    }
                }
            } else {
                filteredData.addAll(originalData)
            }
            return filteredData //movieDataLiveData.postValue(filteredData)
    }

    fun sortByRatingAsc() {
        Executors.newSingleThreadExecutor().execute {
            val movies: MutableList<Movie> = movieDataLiveData.value!!
            movies.sortWith(compareBy { it.rating })
            movieDataLiveData.postValue(movies)
        }
    }

    fun sortByRatingDes() {

        Executors.newSingleThreadExecutor().execute {
            val movies: MutableList<Movie> = movieDataLiveData.value!!
            movies.sortWith(compareBy { it.rating })
            movies.reverse()
            movieDataLiveData.postValue(movies)
        }
    }

    fun sortByRealiseAsc() {
        Executors.newSingleThreadExecutor().execute {
            val movies: MutableList<Movie> = movieDataLiveData.value!!
            movies.sortWith(compareBy { it.year })
            movieDataLiveData.postValue(movies)   //setter
        }
    }

    fun sortByRealiseDes() {
        Executors.newSingleThreadExecutor().execute {
            val movies: MutableList<Movie> = movieDataLiveData.value!!
            movies.sortWith(compareBy { it.year })
            movies.reverse()
            movieDataLiveData.postValue(movies)
        }
    }
}