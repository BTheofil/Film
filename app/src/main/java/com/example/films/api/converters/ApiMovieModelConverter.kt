package com.example.films.api.converters

import com.example.films.api.model.ApiMovie
import com.example.films.api.model.ApiMovieList
import com.example.films.model.Movie
import com.example.films.model.MovieList

object ApiMovieModelConverter {
    fun apiMovieModelToMovieModel(apiMovie: ApiMovie): Movie =
        Movie(
            title = apiMovie.title,
            releaseDate = apiMovie.releaseDate,
            rating = apiMovie.rating,
            details = apiMovie.details,
            image = apiMovie.image,
            categoryArrayList = apiMovie.categoryArrayList
        )


    fun movieModelToApiMovieModel(movie: Movie): ApiMovie =
        ApiMovie(
            title = movie.title,
            releaseDate = movie.releaseDate,
            rating = movie.rating,
            details = movie.details,
            image = movie.image,
            categoryArrayList = movie.categoryArrayList
        )

    fun apiMovieListToMovieList(apiMovieList: ApiMovieList): MovieList {
        val movies = ArrayList<Movie>()
        apiMovieList.list?.forEach { apiMovie ->
            movies.add(apiMovieModelToMovieModel(apiMovie))
        }
        return MovieList(movies)
    }
}