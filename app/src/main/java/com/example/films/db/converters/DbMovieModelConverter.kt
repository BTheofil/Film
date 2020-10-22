package com.example.films.db.converters

import com.example.films.db.entity.DbFavouriteMovieList
import com.example.films.db.entity.FavouriteMovie
import com.example.films.model.Movie
import com.example.films.model.MovieList

object DbMovieModelConverter {

    fun dbMovieModelToMovieModel(favouriteMovie: FavouriteMovie): Movie =
        Movie(
            title = favouriteMovie.title,
            image = favouriteMovie.image,
            releaseDate = favouriteMovie.year,
            rating = favouriteMovie.rate,
            details = favouriteMovie.description,
            categoryArrayList = favouriteMovie.categoryList
        )

    fun dbMovieListToMovieList(favouriteMovieList: DbFavouriteMovieList): MovieList {
        val movies = ArrayList<Movie>()
        favouriteMovieList.list?.forEach { dbMovie ->
            movies.add(dbMovieModelToMovieModel(dbMovie))
        }
        return MovieList(movies)
    }
}