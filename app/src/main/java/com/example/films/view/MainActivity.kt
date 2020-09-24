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
import com.example.films.util.OrderType
import com.example.films.util.SearchType
import com.example.films.view.DetailsActivity.Companion.SELECT_MOVIE
import com.example.films.viewmodel.MovieDataViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var preferences: SharedPreferences

    private lateinit var movieDataViewModel: MovieDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferences = getSharedPreferences(SEARCH_PREFERENCES, Context.MODE_PRIVATE)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> sortDialog(SearchType.RATING)
                R.id.Realise -> sortDialog(SearchType.REALISE)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        initFragment()
        initClickOnMovie()
    }

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

                    movieDataViewModel.search(text)
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        if (item.itemId == R.id.darkButton) {
            val isNightMode: Boolean = ColorUtils.isDarkTheme(this)
            AppCompatDelegate.setDefaultNightMode(
                if (isNightMode) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
            )
            delegate.applyDayNight()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initClickOnMovie(){
        movieDataViewModel.selectedMovieLiveData.observe(this, Observer {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(SELECT_MOVIE, it)
            startActivity(intent)
        })
    }

    private fun initFragment(){
        movieDataViewModel = ViewModelProvider(this).get(MovieDataViewModel::class.java)

        val mainFragment = MainFragment()
        mainFragment.movieViewModel = movieDataViewModel

        supportFragmentManager.beginTransaction()
            .replace(main_fragment_container.id, mainFragment)
            .commit()
    }

    private fun sortDialog(searchType: SearchType) {
        val options = resources.getStringArray(R.array.order_type)

        AlertDialog.Builder(this)
            .setTitle(R.string.Sort_by)
            .setIcon(R.drawable.ic_action_sort_foreground)
            .setItems(options) { _, orderTypePosition ->
                preferences.edit().putInt(SORT_PREFERENCE, orderTypePosition).apply()

                when (orderTypePosition) {
                    OrderType.ASC.ordinal -> doWhenASC(searchType)
                    OrderType.DESC.ordinal -> doWhenDESC(searchType)
                }
            }
            .create().show()
    }

    private fun doWhenASC(searchType: SearchType){
        when (searchType) {
            SearchType.RATING -> movieDataViewModel.sortByRatingAsc()
            SearchType.REALISE -> movieDataViewModel.sortByRealiseAsc()
        }
        Snackbar.make(drawerLayout, R.string.Asc_order, Snackbar.LENGTH_LONG).show()
    }

    private fun doWhenDESC(searchType: SearchType){
        when (searchType) {
            SearchType.RATING -> movieDataViewModel.sortByRatingDes()
            SearchType.REALISE -> movieDataViewModel.sortByRealiseDes()
        }
        Snackbar.make(drawerLayout, R.string.Desc_order, Snackbar.LENGTH_LONG).show()
    }

    companion object{
        const val SEARCH_PREFERENCES = "SEARCH_PREFERENCES"
        const val SORT_PREFERENCE = "SORT"
    }
}


