package com.example.films.retrofit

import com.example.films.model.CategoryList
import com.example.films.retrofit.Retrofit.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryService(private val retrofitAnswer: RetrofitAnswer) : Callback<CategoryList?> {

    fun getCategoryList() {
        val call: Call<CategoryList> = service.listCategory(Retrofit.API_KEY)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<CategoryList?>, response: Response<CategoryList?>) {
        if(response.isSuccessful){
            retrofitAnswer.onSuccessAnswer(response.body() as CategoryList)
        } else{
            retrofitAnswer.onFailureAnswer("StatusCode:" + response.code() + "\n" + response.message())
        }
    }

    override fun onFailure(call: Call<CategoryList?>, t: Throwable) {
        retrofitAnswer.onFailure("Bad call happens!")
    }
}
