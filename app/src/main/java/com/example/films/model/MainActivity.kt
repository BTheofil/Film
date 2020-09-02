package com.example.films.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieAdapter: MovieRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecylerView()
        addDataSet()
    }
    private fun addDataSet(){
        val data = DataSource.createDataSet()
        movieAdapter.submitList(data)
    }

    private fun initRecylerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            movieAdapter = MovieRecyclerAdapter()
            adapter = movieAdapter
        }
    }
}