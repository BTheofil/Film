package com.example.films.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.films.R
import com.example.films.util.ColorUtils
import com.example.films.view.DetailsActivity.Companion.SELECT_MOVIE
import com.example.films.viewmodel.MovieDataViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // menu need that
    private lateinit var toggle: ActionBarDrawerToggle

    // sorting
    private lateinit var preferences: SharedPreferences
    private var ascending: String = "Ascending"
    private var descending: String = "Descending"

    // live data >:(
    private lateinit var movieViewModel: MovieDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // sorting system wizard O_o
        preferences = getSharedPreferences("My_Pref", Context.MODE_PRIVATE)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> sortDialog(0)
                R.id.Realise -> sortDialog(1)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        movieViewModel = ViewModelProvider(this).get(MovieDataViewModel::class.java)

        val mainFragment =MainFragment()
        mainFragment.movieViewModel = movieViewModel

        supportFragmentManager.beginTransaction()
            .replace(main_fragment_container.id ,mainFragment)
            .commit()

        movieViewModel.selectedMovieLiveData.observe(this, Observer {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(SELECT_MOVIE, it)
            startActivity(intent)
        })

    }


    // search
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchItem.collapseActionView()
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {

                    movieViewModel.search(text)
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    // sorting system
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

        when (item.itemId) {
            R.id.darkButton -> {
                val isNightMode: Boolean = ColorUtils.isDarkTheme(this)
                AppCompatDelegate.setDefaultNightMode(
                    if (isNightMode) {
                        AppCompatDelegate.MODE_NIGHT_NO
                    } else AppCompatDelegate.MODE_NIGHT_YES
                )
                delegate.applyDayNight()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


