package com.example.films.model

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieRecyclerAdapter

    // menu need that
    lateinit var toggle: ActionBarDrawerToggle

    // movie storage
    val data = DataSource.createDataSet()

    // sorting
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // sorting system wizard O_o
        preferences = getSharedPreferences("My_Pref", Context.MODE_PRIVATE)
        val sortSettings = preferences.getString("Sort", "Ascending")
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> ratingSelect(sortSettings)

                R.id.Realise -> sortByRealise(movieAdapter)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        initRecyclerView()
        addDataSet()
    }
    private fun ratingSelect(sortSettings: String?) {
        sortDialog()
        if (sortSettings == "Ascending"){
            sortByRatingAsc(movieAdapter)
        }else if (sortSettings == "Descending"){
            sortByRatingDes(movieAdapter)
        }
    }

    private fun sortByRatingAsc(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.rateing })
        movieAdapter.notifyDataSetChanged()
    }

    private fun sortByRatingDes(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.rateing })
        data.reverse()
        movieAdapter.notifyDataSetChanged()
    }

    private fun sortByRealise(movieAdapter: MovieRecyclerAdapter) {
        data.sortWith(compareBy { it.year })
        movieAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun sortDialog() {
        val options = arrayOf("Ascending", "Descending")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sort By")
        builder.setIcon(R.drawable.ic_action_sort_foreground)

        builder.setItems(options) {dialog, which ->
            if (which == 0){
                val editor : SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort", "Ascending")
                editor.apply()
                sortByRatingAsc(movieAdapter)
                Toast.makeText(this@MainActivity, "Ascending Order", Toast.LENGTH_LONG).show()
            }
            if (which == 1){
                val editor : SharedPreferences.Editor = preferences.edit()
                editor.putString("Sort", "Descending")
                editor.apply()
                sortByRatingDes(movieAdapter)
                Toast.makeText(this@MainActivity, "Descending Order", Toast.LENGTH_LONG).show()
            }
        }
        builder.create().show()
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
