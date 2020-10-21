package com.example.films.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.films.util.ProgressState
import com.example.films.model.Movie
import com.example.films.api.service.ApiMovieService
import com.example.films.db.service.DBMovieService
import com.example.films.viewmodel.listener.DataSourceAnswer
import com.example.films.model.MovieList
import com.example.films.util.Language
import java.lang.Exception
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class MovieDataViewModel(application: Application) : AndroidViewModel(application),
    DataSourceAnswer {

    private var originalData: MutableList<Movie> = ArrayList()
    private val apiMovieService = ApiMovieService(this)
    private val dbMovieService =  DBMovieService(application,this)

    val movieDataLiveData: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    val stateChangeLiveData: MutableLiveData<ProgressState> = MutableLiveData()

    init {
        stateChangeLiveData.postValue(ProgressState.LOADING)
        apiMovieService.getMovieList("en-EN")
    }

    override fun onSuccessAnswer(answerObject: Any?) {
        if(answerObject is MovieList) {
            if(answerObject.list !=null) {
                originalData = answerObject.list!!.toMutableList()
                stateChangeLiveData.postValue(ProgressState.FILLED)
            } else {
                originalData = ArrayList()
                stateChangeLiveData.postValue(ProgressState.EMPTY)
            }
            movieDataLiveData.postValue(originalData)
        }else{
            val error = this::class.simpleName + "Wrong answer type!"
            Log.d(ERROR_TAG,error)
            errorLiveData.postValue(error)
            stateChangeLiveData.postValue(ProgressState.EMPTY)
        }
    }

    override fun onFailureAnswer(error: String) {
        errorLiveData.postValue(error)
        stateChangeLiveData.postValue(ProgressState.EMPTY)
    }

    override fun onFailure(error: String) {
        errorLiveData.postValue(error)
        stateChangeLiveData.postValue(ProgressState.EMPTY)
    }

    fun selectLanguage(lan: Language){
        apiMovieService.getMovieList(lan.type)
    }

    fun loadFavouriteMovies(){
        dbMovieService.getAllFavouriteMovie()
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
        const val ERROR_TAG = "MyError"
    }
}
