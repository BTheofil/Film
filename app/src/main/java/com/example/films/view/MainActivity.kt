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

class MainActivity : AppCompatActivity(), MovieRecyclerAdapter.OnMovieItemClickListener {

    private lateinit var movieAdapter: MovieRecyclerAdapter

    // menu need that
    lateinit var toggle: ActionBarDrawerToggle

    // movie storage
    private val data = DataSource.createDataSet()

    // sorting
    lateinit var preferences: SharedPreferences
    private var ascending: String = "Ascending"
    private var descending: String = "Descending"

    private lateinit var movieViewModel: MovieDataViewModel

    // search
    private val searchData: MutableList<Movie> = data

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
                R.id.Rating -> ratingSelect(sortSettings, 0)

                R.id.Realise -> ratingSelect(sortSettings, 1)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        initRecyclerView()
    }

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

                    if (text!!.isEmpty()) {
                        searchData.clear()
                        val search = text.toLowerCase()
                        data.forEach {
                            if (it.title.toLowerCase().contains(search)) {
                                searchData.add(it)
                            }
                        }
                    } else {
                        searchData.clear()
                        searchData.addAll(data)
                    }


                    return true
                }
            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    // these fun from sorting system
    private fun ratingSelect(sortSettings: String?, ratingOrRealise: Int) {
        sortDialog(ratingOrRealise)
        if (sortSettings == ascending) {
            if (ratingOrRealise == 0) {
                sortByRatingAsc(movieAdapter)
            } else if (ratingOrRealise == 1) {
                sortByRealiseAsc(movieAdapter)
            }
        } else if (sortSettings == descending) {
            if (ratingOrRealise == 0) {
                sortByRatingDes(movieAdapter)
            } else if (ratingOrRealise == 1) {
                sortByRealiseDes(movieAdapter)
            }
        }
    }

    private fun sortByRatingAsc(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.rating })
        movieAdapter.notifyDataSetChanged()
    }

    private fun sortByRatingDes(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.rating })
        data.reverse()
        movieAdapter.notifyDataSetChanged()
    }

    private fun sortByRealiseAsc(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.year })
        movieAdapter.notifyDataSetChanged()
    }

    private fun sortByRealiseDes(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.year })
        data.reverse()
        movieAdapter.notifyDataSetChanged()
    }

    private fun sortDialog(ratingOrRealise: Int) {
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
                    sortByRatingAsc(movieAdapter)
                } else if (ratingOrRealise == 1) {
                    sortByRealiseAsc(movieAdapter)
                }
                Toast.makeText(this@MainActivity, "Ascending Order", Toast.LENGTH_LONG).show()
            }
            if (which == 1) {
                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort", descending)
                editor.apply()
                if (ratingOrRealise == 0) {
                    sortByRatingDes(movieAdapter)
                } else if (ratingOrRealise == 1) {
                    sortByRealiseDes(movieAdapter)
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

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingItemDecoration =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            movieAdapter = MovieRecyclerAdapter(this@MainActivity)
            adapter = movieAdapter
        }
        movieViewModel.movieDataLiveData.observe(this,
            Observer<List<Movie>> { movies -> movieAdapter.submitList(movies) })
    }

    // clickable items
    override fun onItemClick(item: Movie, position: Int) {

        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(SELECT_MOVIE, item)
        startActivity(intent)
    }
}


