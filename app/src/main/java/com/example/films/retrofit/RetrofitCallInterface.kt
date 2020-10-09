package com.example.films.retrofit

import com.example.films.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitCallInterface {

    @GET("movie/now_playing")
    fun listRepos(@Query("api_key") api_key : String): Call<MovieList>
}