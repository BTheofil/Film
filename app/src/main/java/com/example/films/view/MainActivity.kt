package com.example.films.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.DataSource
import com.example.films.model.Movie
import com.example.films.view.DetailsActivity.Companion.SELECT_MOVIE
import com.example.films.viewmodel.MovieDataViewModel
import com.example.films.viewmodel.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MovieRecyclerAdapter.OnMovieItemClickListener {

    private lateinit var movieAdapter: MovieRecyclerAdapter

    // menu need that
    lateinit var toggle: ActionBarDrawerToggle

    // sorting
    lateinit var preferences: SharedPreferences
    private var ascending: String = "Ascending"
    private var descending: String = "Descending"

    // live data >:(
    private lateinit var movieViewModel: MovieDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieViewModel = ViewModelProvider(this).get(MovieDataViewModel::class.java)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // sorting system wizard O_o
        preferences = getSharedPreferences("My_Pref", Context.MODE_PRIVATE)
        val sortSettings = preferences.getString("Sort", ascending)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> sortDialog(sortSettings, 0)

                R.id.Realise -> sortDialog(sortSettings, 1)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingItemDecoration =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            movieAdapter = MovieRecyclerAdapter(this@MainActivity)
            adapter = movieAdapter
        }
        // subscribe
        movieViewModel.movieDataLiveData.observe(this,
            Observer<List<Movie>> { movies ->
                movieAdapter.submitList(movies)
            })
    }

    // search
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {

                    movieViewModel.filter(text)
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    // sorting system
    private fun sortDialog(sortSettings: String?, ratingOrRealise: Int) {
        val options = arrayOf(ascending, descending)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sort By")
        builder.setIcon(R.drawable.ic_action_sort_foreground)

        builder.setItems(options) { _, which ->
            if (which == 0) {
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort", ascending)
                editor.apply()
                if (ratingOrRealise == 0) {
                    movieViewModel.sortByRatingAsc()
                } else if (ratingOrRealise == 1) {
                    movieViewModel.sortByRealiseAsc()
                }
                Toast.makeText(this@MainActivity, "Ascending Order", Toast.LENGTH_LONG).show()
            }
            if (which == 1) {
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort", descending)
                editor.apply()
                if (ratingOrRealise == 0) {
                    movieViewModel.sortByRatingDes()
                } else if (ratingOrRealise == 1) {
                    movieViewModel.sortByRealiseDes()
                }
                Toast.makeText(this@MainActivity, "Descending Order", Toast.LENGTH_LONG).show()
            }
        }
        builder.create().show()
    }

    // top menu bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // clickable items
    override fun onItemClick(item: Movie, position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(SELECT_MOVIE, item)
        startActivity(intent)
    }
}


