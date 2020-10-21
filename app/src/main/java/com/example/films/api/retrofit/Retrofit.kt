package com.example.films.api.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    private val client = OkHttpClient.Builder().build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val service: RetrofitCallInterface = retrofit.create(RetrofitCallInterface::class.java)

    const val API_KEY =  "8b8f2601afacb038bd75f8e842dfdfec"
    const val IMAGE_STORAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
}
