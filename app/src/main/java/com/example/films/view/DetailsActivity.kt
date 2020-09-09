package com.example.films.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.films.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        var intent = intent
        movie_titleD.text = intent.getStringExtra("MOVIENAME")
        movie_imageD.setImageResource(intent.getIntExtra("MOVIEIMAGE", 0))
        movie_detailsD.text = intent.getStringExtra("MOVIEDETAILS")
        movie_ratingD.text = intent.getStringExtra("MOVIERATE")
        movie_yearD.text = intent.getStringExtra("MOVIEYEAR")
    }
}