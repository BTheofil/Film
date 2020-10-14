package com.example.films.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.adapter.CategoryRecycleAdapter
import com.example.films.model.Category
import com.example.films.model.CategoryList
import com.example.films.model.Movie
import com.example.films.retrofit.CategoryService
import com.example.films.retrofit.RetrofitAnswer
import com.example.films.util.ImageLoader
import com.example.films.viewmodel.DetailsDataViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.toolbar_on_back.*

class DetailsFragment : Fragment() {

    private lateinit var categoryAdapter: CategoryRecycleAdapter

    private lateinit var detailsDataViewModel: DetailsDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_details, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val selectedMovie = arguments?.get ("movie") as Movie

        detailsDataViewModel = DetailsDataViewModel(requireActivity().application, selectedMovie)
        detailsDataViewModel.categoriesLiveData.observe(viewLifecycleOwner, Observer {categories ->
            categoryAdapter.setData(categories)
        })

        detailsDataViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
            view?.let { Snackbar.make(it, error, Snackbar.LENGTH_LONG).show() }
        })

        selectedMovie.apply {
            titleD_frag.text = getString(R.string.Movie_title,title,year.toString())
            ratingD_frag.text = rating.toString()
            detailsD_frag.text = details
            selectedMovie.image?.let { ImageLoader.loadImage(requireContext(), it, imageD_frag) }
        }

        initRecyclerView()

        back.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun initRecyclerView() {
        categoryD_frag.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            categoryAdapter = CategoryRecycleAdapter()
            adapter = categoryAdapter
        }
    }
}
