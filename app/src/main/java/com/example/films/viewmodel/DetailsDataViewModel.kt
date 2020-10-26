package com.example.films.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.films.model.Category
import com.example.films.model.Movie
import com.example.films.api.service.ApiCategoryService
import com.example.films.db.DB
import com.example.films.db.FavouriteDAO
import com.example.films.db.converters.DbMovieModelConverter
import com.example.films.db.entity.FavouriteMovie
import com.example.films.viewmodel.listener.DataSourceAnswer
import com.example.films.model.CategoryList

class DetailsDataViewModel(application: Application, private val selectedMovie: Movie) : AndroidViewModel(application),
    DataSourceAnswer {

    val categoriesLiveData: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    private lateinit var dao : FavouriteDAO = DB.getInstance().favouriteMovieDAO()
    private var converter: DbMovieModelConverter = DbMovieModelConverter

    init {
        ApiCategoryService(this).getCategoryList()
    }

    override fun onSuccessAnswer(answerObject: Any?) {
        val solvedCategories = ArrayList<Category>()
        if (answerObject is CategoryList) {
            answerObject.list?.forEach { category ->
                selectedMovie.categoryArrayList.forEach { id ->
                    if (category.id == id) {
                        solvedCategories.add(category)
                    }
                }
            }
        }
        categoriesLiveData.value = solvedCategories
    }

    override fun onFailureAnswer(error: String) {
        errorLiveData.value = error
    }

    override fun onFailure(error: String) {
        errorLiveData.value = error
    }

    fun addFavourite(movie: Movie){
        val fmovie = movie.image?.let {
            FavouriteMovie(
                id = 0,
                title = movie.title,
                image = it,
                releaseDate = movie.year.toLong(),
                rate = movie.rating,
                description = movie.details,
                categoryList = converter.ArrayListToStringCategory(movie.categoryArrayList))
        }
        if (fmovie != null) {
            dao.addMovie(fmovie)
        }
    }
}