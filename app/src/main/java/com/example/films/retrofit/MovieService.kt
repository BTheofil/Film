package com.example.films.retrofit

import com.example.films.model.MovieList
import com.example.films.retrofit.Retrofit.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieService(private val retrofitAnswer: RetrofitAnswer) : Callback<MovieList?> {

    fun getMovieList(selectedLang: String) {
        val call: Call<MovieList> = service.listMovies(Retrofit.API_KEY, selectedLang)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<MovieList?>, response: Response<MovieList?>) {
        if(response.isSuccessful){
            retrofitAnswer.onSuccessAnswer(response.body() as MovieList)
        } else{
            retrofitAnswer.onFailureAnswer("StatusCode:" + response.code() + "\n" + response.message())
        }
    }

    override fun onFailure(call: Call<MovieList?>, t: Throwable) {
        retrofitAnswer.onFailure("Bad call happens!")
    }
}
