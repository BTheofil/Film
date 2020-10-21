package com.example.films.adapter.listener

import com.example.films.model.Movie

interface AdapterListener {
    fun onClickItem(movie: Movie)
}
