package com.example.films.retrofit

interface RetrofitAnswer {
    fun onSuccessAnswer(answerObject : Any?)
    fun onFailureAnswer(error : String)
    fun onFailure(error : String)
}
