package com.example.films.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betterfilms.R
import com.example.betterfilms.adapter.CategoryRecycleAdapter
import com.example.betterfilms.model.Category
import com.example.betterfilms.model.Movie
import com.example.films.R
import com.example.films.R.*
import com.example.films.adapter.CategoryRecycleAdapter
import com.example.films.model.Category
import com.example.films.model.Movie
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.toolbar_on_back.*


class DetailsFragment : Fragment() {

    private lateinit var selectedMovie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        selectedMovie = arguments?.get ("movie") as Movie

        selectedMovie.apply {
            titleD_frag.text = getString(R.string.Movie_title,title,year.toString())
            ratingD_frag.text = rating.toString()
            detailsD_frag.text = details
            imageD_frag.setImageResource(image)

            initRecyclerView(categoryArrayList)
        }

        back.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerView(categories: List<Category>) {
        categoryD_frag.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val categoryAdapter =
                CategoryRecycleAdapter()
            categoryAdapter.setData(categories)
            adapter = categoryAdapter
        }
    }
}
