package com.example.films

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.films.view.MovieRecyclerAdapter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        actionBar.setTitle(aTitle)
        movie_title.text = aTitle

        //recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.adapter = MovieRecyclerAdapter()
    }
}
