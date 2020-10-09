package com.example.films.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.films.R
import com.example.films.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieDataViewModel: MovieDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieDataViewModel = ViewModelProvider(this).get(MovieDataViewModel::class.java)

        initFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initFragment(){

        val mainFragment = MainFragment()
        mainFragment.movieDataViewModel = movieDataViewModel

    }

    companion object{
        const val SEARCH_PREFERENCES = "SEARCH_PREFERENCES"
        const val SORT_PREFERENCE = "SORT"
    }
}
