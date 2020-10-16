package com.example.films.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.films.util.ProgressState
import com.example.films.model.Movie
import com.example.films.model.MovieList
import com.example.films.retrofit.MovieService
import com.example.films.retrofit.RetrofitAnswer
import com.example.films.util.Language
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class MovieDataViewModel(application: Application) : AndroidViewModel(application), RetrofitAnswer {

    private var originalData: MutableList<Movie> = ArrayList()
    private val movieService = MovieService(this)

    val movieDataLiveData: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val stateChangeLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    init {
        stateChangeLiveData.value = ProgressState.LOADING
        movieService.getMovieList("en-EN")
    }

    override fun onSuccessAnswer(answerObject: Any?) {
        if(answerObject is MovieList) {
            if(answerObject.list !=null) {
                originalData = answerObject.list!!.toMutableList()
                stateChangeLiveData.value = ProgressState.FILLED
            } else {
                originalData = ArrayList()
                stateChangeLiveData.value = ProgressState.EMPTY
            }
            movieDataLiveData.value = originalData
        }else{
            val error = this::class.simpleName + "Wrong answer type!"
            Log.d(RETROFIT_TAG,error)
            errorLiveData.value = error
            stateChangeLiveData.value = ProgressState.EMPTY
        }
    }

    override fun onFailureAnswer(error: String) {
        errorLiveData.value = error
        stateChangeLiveData.value = ProgressState.EMPTY
    }

    override fun onFailure(error: String) {
        errorLiveData.value = error
        stateChangeLiveData.value = ProgressState.EMPTY
    }

    fun selectLanguage(lan: Language){
        movieService.getMovieList(lan.s)
    }

    fun search(pattern: String?) {
        Executors.newSingleThreadExecutor().execute {
            val r = filter(pattern)
            movieDataLiveData.postValue(r)
        }
    }

    fun sortByRatingAsc() {
        Executors.newSingleThreadExecutor().execute {
            try {
                val movies: MutableList<Movie> = movieDataLiveData.value!!
                movies.sortWith(compareBy { it.rating })
                movieDataLiveData.postValue(movies)
            }catch (e: Exception){
                return@execute
            }
        }
    }

    fun sortByRatingDes() {
        Executors.newSingleThreadExecutor().execute {
            try {
            val movies: MutableList<Movie> = movieDataLiveData.value!!
            movies.sortWith(compareBy { it.rating })
            movies.reverse()
            movieDataLiveData.postValue(movies)
            }catch (e: Exception){
                return@execute
            }
        }
    }

    fun sortByRealiseAsc() {
        Executors.newSingleThreadExecutor().execute {
            try {
            val movies: MutableList<Movie> = movieDataLiveData.value!!
            movies.sortWith(compareBy { it.year })
            movieDataLiveData.postValue(movies)
            }catch (e: Exception){
                return@execute
            }
        }
    }

    fun sortByRealiseDes() {
        Executors.newSingleThreadExecutor().execute {
            try {
            val movies: MutableList<Movie> = movieDataLiveData.value!!
            movies.sortWith(compareBy { it.year })
            movies.reverse()
            movieDataLiveData.postValue(movies)
            }catch (e: Exception){
                return@execute
            }
        }
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
        return filteredData
    }

    companion object{
        const val RETROFIT_TAG ="retrofit"
    }
}
