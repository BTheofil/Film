package com.example.films.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.toolbar_on_back.*

class DetailsFragment : Fragment(), RetrofitAnswer {

    private lateinit var selectedMovie: Movie

    private lateinit var categoryAdapter: CategoryRecycleAdapter

    private val categoryService = CategoryService(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_details, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        selectedMovie = arguments?.get ("movie") as Movie

        selectedMovie.apply {
            titleD_frag.text = getString(R.string.Movie_title,title,year.toString())
            ratingD_frag.text = rating.toString()
            detailsD_frag.text = details
            selectedMovie.image?.let { ImageLoader.loadImage(requireContext(), it, imageD_frag) }
        }

        initRecyclerView()
        categoryService.getCategoryList()

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

    override fun onSuccessAnswer(answerObject: Any?) {
        if(answerObject is CategoryList){
            val categoriesAdapterData: ArrayList<Category> = ArrayList()
            selectedMovie.categoryArrayList.forEach{id->
                answerObject.list?.forEach { category ->
                    if(id == category.id) {
                        categoriesAdapterData.add(category)
                    }
                }
            }
            categoryAdapter.setData(categoriesAdapterData)
        }
    }

    override fun onFailureAnswer(error: String) {
        Log.d("retrofit",error)
    }

    override fun onFailure(error: String) {
        Log.d("retrofit",error)
    }
}
