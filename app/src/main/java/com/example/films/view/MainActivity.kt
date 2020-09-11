package com.example.films.view

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.Movie
import com.example.films.view.DetailsActivity.Companion.SELECT_MOVIE
import com.example.films.viewmodel.MovieDataViewModel
import com.example.films.viewmodel.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieRecyclerAdapter.OnMovieItemClickListener {

    private lateinit var movieAdapter: MovieRecyclerAdapter

    // menu need that
    lateinit var toggle: ActionBarDrawerToggle

    // sorting
    private lateinit var preferences: SharedPreferences
    private var ascending: String = "Ascending"
    private var descending: String = "Descending"

    // live data >:(
    private lateinit var movieViewModel: MovieDataViewModel

//    private var eyp: ClipData.Item? = null
//    internal lateinit var sharedPref: SharedPref

//    private val appSettingsPrefs: SharedPreferences = getSharedPreferences("AppSettingsPrefs", 0)
//    private val sharedPrefsEdit: SharedPreferences.Editor = appSettingsPrefs.edit()
//    private val isNightModeOn: Boolean = appSettingsPrefs.getBoolean("NightMode", false)

    override fun onCreate(savedInstanceState: Bundle?) {
//        sharedPref = SharedPref(this)
//        if (sharedPref.loadNightModeState() == true) {
//            setTheme(R.style.DarkTheme)
//        } else {
//            setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieViewModel = ViewModelProvider(this).get(MovieDataViewModel::class.java)

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

        initRecyclerView()

//        eyp = findViewById<View>(R.id.darkButton) as ClipData.Item?
//        if (sharedPref.loadNightModeState() == true) {
//            eyp!!.isChecked = true
//        }
//
//        eyp!!.setOnCheckedChangeListener {, isChecked ->
//            if (isChecked) {
//                sharedPref.setNightModeState(true)
//                restartApp()
//            } else {
//                sharedPref.setNightModeState(false)
//                restartApp()
//            }
//        }
//        }
//        if (isNightModeOn) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
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

//       if (check(R.id.darkButton)) {
////            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
////            return true
////        }

        val searchItem = menu.findItem(R.id.search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    searchItem.collapseActionView()
                    return true
                }

                override fun onQueryTextChange(text: String?): Boolean {

                    movieViewModel.filter(text)
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

//        if (item.itemId == R.id.darkButton) {
//
//            if (isNightModeOn) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                sharedPrefsEdit.putBoolean("NightMode", false)
//                sharedPrefsEdit.apply()
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                sharedPrefsEdit.putBoolean("NightMode", true)
//                sharedPrefsEdit.apply()
//            }
//        }
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        return super.onOptionsItemSelected(item)
    }

    // clickable items
    override fun onItemClick(item: Movie, position: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(SELECT_MOVIE, item)
        startActivity(intent)
    }

//    fun restartApp() {
//        val i = Intent(applicationContext, MainActivity::class.java)
//        startActivity(i)
//        finish()
//    }
}


