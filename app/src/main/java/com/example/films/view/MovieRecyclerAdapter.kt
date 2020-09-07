package com.example.films.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.films.R
import com.example.films.model.Movie
import kotlinx.android.synthetic.main.layout_movies_list_item.view.*

class MovieRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_movies_list_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        MovieViewHolder(holder.itemView).bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(movieList: List<Movie>) {
        items = movieList
        notifyDataSetChanged()
    }

    class MovieViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val movieImage = itemView.movie_image
        val movieTitle = itemView.movie_title
        val movieYear = itemView.movie_year
        val movieRating = itemView.movie_rating
        val movieDetails = itemView.movie_details

        fun bind(movie: Movie) {
            movieTitle.text = movie.title
            movieYear.text = movie.year.toString()
            movieRating.text = movie.rateing.toString()
            movieDetails.text = movie.details

            val requestOption = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOption)
                .load(movie.image)
                .into(movieImage)
        }
    }
}
