package com.example.films.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import com.example.films.R
import com.example.films.model.Movie
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details_category.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val item = intent.getSerializableExtra(SELECT_MOVIE) as Movie
        movie_titleD.text = item.title
        movie_detailsD.text = item.details
        movie_ratingD.text = item.rating.toString()
        movie_yearD.text = item.year.toString()
        movie_imageD.setImageResource(item.image)
        //topic.text = item.categoryArrayList[0]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val SELECT_MOVIE = "selectMovie"
    }
}