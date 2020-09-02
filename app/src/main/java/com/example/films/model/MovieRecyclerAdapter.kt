package com.example.films.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.films.R
import kotlinx.android.synthetic.main.layout_movies_list_item.view.*

class MovieRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_movies_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is MovieViewHolder ->{
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(movieList: List<Movie>){
        items = movieList
    }

    class MovieViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val movieImage = itemView.movie_image
        val movieTitle = itemView.movie_title
        val movieYear = itemView.movie_year
        val movieRating = itemView.movie_rating
        val movieDetails = itemView.movie_details

        fun bind(movie: Movie){

            movieTitle.setText(movie.title)
            movieYear.setText(movie.year.toString())
            movieRating.setText(movie.rating.toString())
            movieDetails.setText(movie.details)

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