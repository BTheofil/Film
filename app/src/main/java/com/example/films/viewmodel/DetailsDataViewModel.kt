package com.example.films.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.films.model.Category
import com.example.films.model.CategoryList
import com.example.films.model.Movie
import com.example.films.retrofit.CategoryService
import com.example.films.retrofit.RetrofitAnswer

class DetailsDataViewModel(application: Application, private val selectedMovie: Movie) : AndroidViewModel(application), RetrofitAnswer {

    val categoriesLiveData: MutableLiveData<MutableList<Category>> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        CategoryService(this).getCategoryList()
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

    companion object{
        const val RETROFIT_TAG ="retrofit"
    }
}