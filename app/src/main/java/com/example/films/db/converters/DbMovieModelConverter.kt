package com.example.films.db.converters

import com.example.films.db.entity.DbFavouriteMovieList
import com.example.films.db.entity.FavouriteMovie
import com.example.films.model.Movie
import com.example.films.model.MovieList
import java.util.*
import kotlin.collections.ArrayList

object DbMovieModelConverter {

    fun dbMovieModelToMovieModel(favouriteMovie: FavouriteMovie): Movie {

        return Movie(
            title = favouriteMovie.title,
            image = favouriteMovie.image,
            releaseDate = Date(favouriteMovie.releaseDate),
            rating = favouriteMovie.rate,
            details = favouriteMovie.description,
            categoryArrayList = stringToArrayListCategory(favouriteMovie.categoryList)
        )
    }

    fun dbMovieListToMovieList(favouriteMovieList: DbFavouriteMovieList): MovieList {
        val movies = ArrayList<Movie>()
        favouriteMovieList.list?.forEach { dbMovie ->
            movies.add(dbMovieModelToMovieModel(dbMovie))
        }
        return MovieList(movies)
    }

    private fun stringToArrayListCategory(categoryListString: String): ArrayList<Int>{
        val result = categoryListString.removeSurrounding("[", "]").split(",").map { it.toInt() }
        return ArrayList(result)
    }

    fun ArrayListToStringCategory(categoryListString: ArrayList<Int>): String{
        return categoryListString.joinToString()
    }

}