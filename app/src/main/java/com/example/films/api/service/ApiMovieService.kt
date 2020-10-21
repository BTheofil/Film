package com.example.films.api.service

import com.example.films.api.converters.ApiMovieModelConverter
import com.example.films.api.model.ApiMovieList
import com.example.films.api.retrofit.Retrofit
import com.example.films.api.retrofit.Retrofit.service
import com.example.films.viewmodel.listener.DataSourceAnswer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiMovieService(private val dataSourceAnswer: DataSourceAnswer) : Callback<ApiMovieList?> {

    fun getMovieList(selectedLang: String) {
        val call: Call<ApiMovieList> = service.listMovies(Retrofit.API_KEY, selectedLang)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<ApiMovieList?>, response: Response<ApiMovieList?>) {
        if(response.isSuccessful){
            dataSourceAnswer.onSuccessAnswer(ApiMovieModelConverter.apiMovieListToMovieList(response.body()!!))
        } else{
            dataSourceAnswer.onFailureAnswer("StatusCode:" + response.code() + "\n" + response.message())
        }
    }

    override fun onFailure(call: Call<ApiMovieList?>, t: Throwable) {
        dataSourceAnswer.onFailure("Bad call happens!")
    }
}
