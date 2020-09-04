package com.example.films.model

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.prefs.AbstractPreferences

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieRecyclerAdapter

    // menu need that
    lateinit var toggle: ActionBarDrawerToggle

    // movie storage
    val data = DataSource.createDataSet()

    //sorting
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> sortByRating(movieAdapter)
                R.id.Realise -> sortByRealise(movieAdapter)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        initRecyclerView()
        addDataSet()
    }

    private fun sortByRealise(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.year })
        movieAdapter.notifyDataSetChanged()
    }

    private fun sortByRating(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.rateing })
        movieAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun addDataSet() {
        movieAdapter.submitList(data)
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            movieAdapter = MovieRecyclerAdapter()
            adapter = movieAdapter
        }
    }
}
