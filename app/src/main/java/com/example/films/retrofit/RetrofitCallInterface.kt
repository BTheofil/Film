package com.example.films.retrofit

import com.example.films.model.CategoryList
import com.example.films.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitCallInterface {

    @GET("movie/now_playing")
    fun listMovies(@Query("api_key") api_key : String): Call<MovieList>

    @GET("genre/movie/list")
    fun listCategory(@Query("api_key") api_key : String): Call<CategoryList>
}
