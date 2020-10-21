package com.example.films.db.service

import android.content.Context
import com.example.films.R
import com.example.films.db.DB
import com.example.films.db.FavouriteDAO
import com.example.films.db.entity.FavouriteMovie
import com.example.films.viewmodel.listener.DataSourceAnswer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DBMovieService(private val context: Context, private val dataSourceAnswer: DataSourceAnswer) {

    private val dao: FavouriteDAO = DB.getInstance(context).favouriteMovieDAO()

    fun getAllFavouriteMovie() {
        GlobalScope.launch {
            val favourites: List<FavouriteMovie> = dao.getAll()
            if (favourites.isEmpty()) {
                dataSourceAnswer.onFailureAnswer(context.getString(R.string.database_empty))
            } else {
                dataSourceAnswer.onSuccessAnswer("")//TODO converted movies list
            }
        }
    }
}