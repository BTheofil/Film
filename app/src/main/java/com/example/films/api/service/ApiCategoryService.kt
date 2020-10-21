package com.example.films.api.service

import com.example.films.api.converters.ApiCategoryModelConverter
import com.example.films.api.model.ApiCategoryList
import com.example.films.api.retrofit.Retrofit
import com.example.films.api.retrofit.Retrofit.service
import com.example.films.viewmodel.listener.DataSourceAnswer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCategoryService(private val dataSourceAnswer: DataSourceAnswer) : Callback<ApiCategoryList?> {

    fun getCategoryList() {
        val call: Call<ApiCategoryList> = service.listCategory(Retrofit.API_KEY)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<ApiCategoryList?>, response: Response<ApiCategoryList?>) {
        if(response.isSuccessful){
            dataSourceAnswer.onSuccessAnswer(ApiCategoryModelConverter.apiCategoryListToCategoryList(response.body()!!))
        } else{
            dataSourceAnswer.onFailureAnswer("StatusCode:" + response.code() + "\n" + response.message())
        }
    }

    override fun onFailure(call: Call<ApiCategoryList?>, t: Throwable) {
        dataSourceAnswer.onFailure("Bad call happens!")
    }
}
