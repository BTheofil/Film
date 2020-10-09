package com.example.films.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.films.R
import com.example.films.adapter.CategoryRecycleAdapter
import com.example.films.model.Movie
import com.example.films.retrofit.Retrofit
import com.example.films.util.ImageLoader
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.toolbar_on_back.*


class DetailsFragment : Fragment() {

    private lateinit var selectedMovie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        selectedMovie = arguments?.get ("movie") as Movie

        selectedMovie.apply {
            titleD_frag.text = getString(R.string.Movie_title,title,year.toString())
            ratingD_frag.text = rating.toString()
            detailsD_frag.text = details
            selectedMovie.image?.let { ImageLoader.loadImage(requireContext(), it, imageD_frag) }

            initRecyclerView(categoryArrayList)
        }

        back.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerView(categories: List<Int>) {
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
