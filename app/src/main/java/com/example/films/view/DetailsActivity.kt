package com.example.films.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.films.R
import com.example.films.model.Movie
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        /*var intent = intent
        movie_titleD.text = intent.getStringExtra("MOVIENAME")
        movie_imageD.setImageResource(intent.getIntExtra("MOVIEIMAGE", 0))
        movie_detailsD.text = intent.getStringExtra("MOVIEDETAILS")
        movie_ratingD.text = intent.getStringExtra("MOVIERATE")
        movie_yearD.text = intent.getStringExtra("MOVIEYEAR")*/

        val item = intent.getSerializableExtra(SELECT_MOVIE) as Movie
        movie_titleD.text = item.title
        movie_detailsD.text = item.details
        movie_ratingD.text = item.rateing.toString()
        movie_yearD.text = item.year.toString()
        movie_imageD.setImageResource(item.image)
    }
    companion object{
        const val SELECT_MOVIE = "selectMovie"
    }
}