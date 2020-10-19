package com.example.films.listener

import com.example.films.model.network.Movie

interface AdapterListener {
    fun onClickItem(movie: Movie)
}
