package com.example.films.retrofit

import com.example.films.model.MovieList
import com.example.films.retrofit.Retrofit.retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieService(private val retrofitAnswer: RetrofitAnswer) : Callback<MovieList?> {

    private val service: RetrofitCallInterface = retrofit.create(RetrofitCallInterface::class.java)

    fun getMovieList() {
        val call: Call<MovieList> = service.listRepos(Retrofit.API_KEY)
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