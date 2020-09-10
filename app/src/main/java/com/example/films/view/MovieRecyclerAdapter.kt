package com.example.films.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.films.R
import com.example.films.model.Movie
import kotlinx.android.synthetic.main.layout_movies_list_item.view.*

class MovieRecyclerAdapter(var clickListener: OnMovieItemClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v = MovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.layout_movies_list_item, parent, false)
        )
        return v
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        MovieViewHolder(holder.itemView).bind(items[position], clickListener)
    }

    override fun getItemCount() = items.size

    fun submitList(movieList: List<Movie>) {
        items = movieList
        notifyDataSetChanged()
    }

    class MovieViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieImage: ImageView = itemView.movie_image
        private val movieTitle: TextView = itemView.movie_title
        private val movieYear: TextView = itemView.movie_year
        private val movieRating: TextView = itemView.movie_rating
        private val movieDetails: TextView = itemView.movie_details

        fun bind(movie: Movie, action: OnMovieItemClickListener) {
            movieTitle.text = movie.title
            movieYear.text = movie.year.toString()
            movieRating.text = movie.rating.toString()
            movieDetails.text = movie.details

            itemView.setOnClickListener{
                action.onItemClick(movie, adapterPosition)
            }

            val requestOption = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOption)
                .load(movie.image)
                .into(movieImage)
        }
    }

    interface OnMovieItemClickListener {
        fun onItemClick(item: Movie, position: Int)
    }
}
