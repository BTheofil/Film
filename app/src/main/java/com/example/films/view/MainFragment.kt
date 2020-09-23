package com.example.films.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.Movie
import com.example.films.viewmodel.MovieDataViewModel
import com.example.films.viewmodel.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment() : Fragment(), MovieRecyclerAdapter.OnMovieItemClickListener {

    var movieViewModel: MovieDataViewModel? = null
    private lateinit var movieAdapter: MovieRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            movieAdapter = MovieRecyclerAdapter(this@MainFragment)
            adapter = movieAdapter
        }
        // subscribe
        movieViewModel?.movieDataLiveData?.observe(viewLifecycleOwner, Observer<List<Movie>> { movies ->
                movieAdapter.submitList(movies)
            })
    }

    override fun onItemClick(item: Movie, position: Int) {
        movieViewModel?.selectMovie(item)
    }
}
