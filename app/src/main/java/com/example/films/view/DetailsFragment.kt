package com.example.films.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.Category
import com.example.films.model.Movie
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var categoryAdapter: CategoryRecyclerAdapter
    var movie: Movie? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movie?.apply {
            titleD_frag.text = title
            yearD_frag.text = year.toString()
            ratingD_frag.text = rating.toString()
            imageD_frag.setImageResource(image)

            initRecyclerView(categoryArrayList)
        }
    }

    private fun initRecyclerView(categories: List<Category>) {
        categoryD_frag.apply {
            layoutManager = LinearLayoutManager(
                this@DetailsFragment.requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            categoryAdapter = CategoryRecyclerAdapter()
            categoryAdapter.setData(categories)
            adapter = categoryAdapter
        }
    }
}
