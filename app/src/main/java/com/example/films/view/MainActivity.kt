package com.example.films.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.films.R
import com.example.films.view.DetailsActivity.Companion.SELECT_MOVIE
import com.example.films.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieDataViewModel: MovieDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieDataViewModel = ViewModelProvider(this).get(MovieDataViewModel::class.java)

        initFragment()
        initClickOnMovie()
    }

    private fun initClickOnMovie(){
        movieDataViewModel.selectedMovieLiveData.observe(this, Observer {
            val intent = Intent(this, DetailsFragment::class.java)
            intent.putExtra(SELECT_MOVIE, it)
            startActivity(intent)
        })
    }

    private fun initFragment(){

        val mainFragment = MainFragment()
        mainFragment.movieViewModel = movieDataViewModel

        supportFragmentManager.beginTransaction()
            .replace(main_fragment_container.id, mainFragment)
            .commit()
    }

    companion object{
        const val SEARCH_PREFERENCES = "SEARCH_PREFERENCES"
        const val SORT_PREFERENCE = "SORT"
    }
}


