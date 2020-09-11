package com.example.films.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.Category
import com.example.films.model.Movie
import com.example.films.viewmodel.CategoryDataViewModel
import com.example.films.viewmodel.MovieDataViewModel
import com.example.films.viewmodel.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_category.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailsActivity : AppCompatActivity() {

    // live data >:(
    private lateinit var categoryViewModel: CategoryDataViewModel
    private lateinit var categoryAdapter: CategoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        categoryViewModel = ViewModelProvider(this).get(CategoryDataViewModel::class.java)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val item = intent.getSerializableExtra(SELECT_MOVIE) as Movie
        movie_titleD.text = item.title
        movie_detailsD.text = item.details
        movie_ratingD.text = item.rating.toString()
        movie_yearD.text = item.year.toString()
        movie_imageD.setImageResource(item.image)
        //topic.text = item.categoryArrayList.toString()

        initRecyclerView()
    }
    private fun initRecyclerView() {
        categoryD.apply {
            categoryAdapter = CategoryRecyclerAdapter()
            adapter = categoryAdapter
        }
//        movieViewModel.movieDataLiveData.observe(this,
//            Observer<List<Movie>> { movies ->
//                movieAdapter.submitList(movies)
//            })
//        categoryViewModel.categoryDataLiveData.observe(this,
//            Observer<List<Category>> { c ->
//                categoryAdapter.submitList(c)
//            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val SELECT_MOVIE = "selectMovie"
    }
}
