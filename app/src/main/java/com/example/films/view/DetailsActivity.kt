package com.example.films.view

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.Category
import com.example.films.model.Movie
import com.example.films.util.ColorUtils
import com.example.films.viewmodel.CategoryDataViewModel
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    // live data >:(
    private lateinit var categoryViewModel: CategoryDataViewModel
    private lateinit var categoryAdapter: CategoryRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        categoryViewModel = ViewModelProvider(this).get(CategoryDataViewModel::class.java)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val item = intent.getSerializableExtra(SELECT_MOVIE) as Movie
        movie_titleD.text = item.title
        movie_detailsD.text = item.details
        movie_ratingD.text = item.rating.toString()
        movie_yearD.text = item.year.toString()
        movie_imageD.setImageResource(item.image)

        initRecyclerView(item.categoryArrayList)


        val isNightMode: Boolean = ColorUtils.isDarkTheme(this)

        btn.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(if(isNightMode){
                AppCompatDelegate.MODE_NIGHT_NO
            } else AppCompatDelegate.MODE_NIGHT_YES)

            delegate.applyDayNight()
        }
    }
    private fun initRecyclerView(categories :List<Category>) {
        categoryD.apply {
            layoutManager = LinearLayoutManager(this@DetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            categoryAdapter = CategoryRecyclerAdapter()
            categoryAdapter.setData(categories)
            adapter = categoryAdapter
        }

    }

    companion object{
        const val SELECT_MOVIE = "selectMovie"
    }
}
