package com.example.films.viewmodel.listener

interface DataSourceAnswer {
    fun onSuccessAnswer(answerObject : Any?)
    fun onFailureAnswer(error : String)
    fun onFailure(error : String)
}
