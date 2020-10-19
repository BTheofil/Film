package com.example.films.retrofit

import com.example.films.model.network.CategoryList
import com.example.films.model.network.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitCallInterface {

    @GET("movie/now_playing")
    fun listMovies(
        @Query("api_key") apiKey : String,
        @Query("language") language : String
    ): Call<MovieList>

    @GET("genre/movie/list")
    fun listCategory(
        @Query("api_key") apiKey : String
    ): Call<CategoryList>
}
