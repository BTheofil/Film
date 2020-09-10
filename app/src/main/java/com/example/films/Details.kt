package com.example.films

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // load data to activity
        //movie_imageD.setImageResource(getIntent().getStringExtra("poster").toInt())
        movie_titleD.text = intent.getStringExtra("title")
        movie_yearD.text = intent.getStringExtra("year")
        movie_ratingD.text = intent.getStringExtra("rate")
        movie_detailsD.text = intent.getStringExtra("description")

    }
}