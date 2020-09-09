package com.example.films.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.films.R
import com.example.films.model.Movie
import kotlinx.android.synthetic.main.activity_details.*

class Details : AppCompatActivity() {

   // lateinit var binding : DetailsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        var intent = intent
        movie_titleD.text = intent.getStringExtra("MOVIENAME")
        movie_imageD.setImageResource(intent.getIntExtra("MOVIEIMAGE", 0))
        movie_detailsD.text = intent.getStringExtra("MOVIEDETAILS")
        movie_ratingD.text = intent.getStringExtra("MOVIERATE")
        movie_yearD.text = intent.getStringExtra("MOVIEYEAR")

        //val item = intent.getSerializableExtra(EXTRA_ITEM) as Movie
        //movie_titleD.text = item.title

       /*
        movie_titleD.text = getIntent().getStringExtra("MOVIENAME")
        movie_imageD.setImageResource(getIntent().getStringExtra("MOVIEIMAGE", 0))
        movie_detailsD.text = getIntent().getStringExtra("MOVIEDETAILS")
        movie_ratingD.text = getIntent().getStringExtra("MOVIERATE")
        movie_yearD.text = getIntent().getStringExtra("MOVIEYEAR")*/

        /*
        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")


        actionBar.setTitle(movie_titleD)
        movie_title.text = aTitle*/

        //recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.adapter = MovieRecyclerAdapter()
    }

    companion object{
        const val EXTRA_ITEM = "alma"
    }
}
