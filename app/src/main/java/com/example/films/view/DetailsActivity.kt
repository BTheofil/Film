package com.example.films.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.Category
import com.example.films.model.Movie
import com.example.films.viewmodel.CategoryDataViewModel
import kotlinx.android.synthetic.main.activity_details.*


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val movie = (intent.getSerializableExtra(SELECT_MOVIE)) as Movie
        val detailsFragment = DetailsFragment()
        detailsFragment.movie = movie
        supportFragmentManager.beginTransaction().replace(details_fragment_container.id,detailsFragment ).commit()
    }

    companion object{
        const val SELECT_MOVIE = "selectMovie"
    }
}
