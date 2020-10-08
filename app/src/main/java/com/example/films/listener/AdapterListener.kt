package com.example.films.listener

import com.example.films.model.Movie

interface AdapterListener {
    fun onClickItem(movie: Movie)
}