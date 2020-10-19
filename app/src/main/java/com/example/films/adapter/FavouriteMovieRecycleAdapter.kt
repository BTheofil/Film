package com.example.films.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.films.R
import com.example.films.listener.AdapterListener
import com.example.films.model.entity.FavouriteMovie
import com.example.films.model.network.Movie
import com.example.films.util.ImageLoader
import kotlinx.android.synthetic.main.list_movie.view.*

class FavouriteMovieRecycleAdapter(private var adapterListener: AdapterListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    private var items: List<FavouriteMovie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MovieViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_movie, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        MovieViewHolder(holder.itemView)
            .bind(items[position])
    }

    override fun getItemCount() = items.size

    fun submitList(movieList: List<FavouriteMovie>) {
        items = movieList
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieImage: ImageView = itemView.fempty_image
        private val movieTitle: TextView = itemView.fmovie_title
        private val movieRating: TextView = itemView.fmovie_rating
        private val movieDetails: TextView = itemView.fmovie_details

        fun bind(movie: FavouriteMovie) {

            movie.apply {
                movieTitle.text = itemView.context.getString(R.string.Movie_title,title,year.toString())
                movieRating.text = rate
                movieDetails.text = description
            }

            movie.image.let { ImageLoader.loadImage(itemView.context, it, movieImage) }
        }

    }
}
