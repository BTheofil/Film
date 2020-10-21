package com.example.films.api.retrofit

import com.example.films.api.model.ApiCategoryList
import com.example.films.api.model.ApiMovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitCallInterface {

    @GET("movie/now_playing")
    fun listMovies(
        @Query("api_key") apiKey : String,
        @Query("language") language : String
    ): Call<ApiMovieList>

    @GET("genre/movie/list")
    fun listCategory(
        @Query("api_key") apiKey : String
    ): Call<ApiCategoryList>
}
