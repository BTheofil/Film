package com.example.films.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.model.Movie
import com.example.films.util.ColorUtils
import com.example.films.util.OrderType
import com.example.films.util.SearchType
import com.example.films.viewmodel.MovieDataViewModel
import com.example.films.viewmodel.TopSpacingItemDecoration
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_main.*


class MainFragment() : Fragment(), MovieRecyclerAdapter.OnMovieItemClickListener {

    var movieViewModel: MovieDataViewModel? = null
    private lateinit var movieAdapter: MovieRecyclerAdapter
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        initSwitchNightMode()
        initSearchView()
        initDrawer()
    }

    override fun onItemClick(item: Movie, position: Int) {
        movieViewModel?.selectMovie(item)
    }

    private fun initDrawer() {

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        (activity as AppCompatActivity).apply {
            setSupportActionBar(toolbar)
            //TODO toggle button beállitása
        }

        preferences = requireActivity().getSharedPreferences(MainActivity.SEARCH_PREFERENCES, Context.MODE_PRIVATE)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> sortDialog(SearchType.RATING)
                R.id.Realise -> sortDialog(SearchType.REALISE)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun initSearchView() {
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchview.onActionViewCollapsed()
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                movieViewModel?.search(text)
                return false
            }
        })
    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingItemDecoration =
                TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingItemDecoration)
            movieAdapter = MovieRecyclerAdapter(this@MainFragment)
            adapter = movieAdapter
        }
        // subscribe
        movieViewModel?.movieDataLiveData?.observe(
            viewLifecycleOwner,
            Observer<List<Movie>> { movies ->
                movieAdapter.submitList(movies)
            })
    }

    private fun initSwitchNightMode() {
        bt_night.setOnClickListener {
            val isNightMode: Boolean = ColorUtils.isDarkTheme(requireActivity())
            AppCompatDelegate.setDefaultNightMode(
                if (isNightMode) {
                    AppCompatDelegate.MODE_NIGHT_NO
                } else {
                    AppCompatDelegate.MODE_NIGHT_YES
                }
            )
            (activity as AppCompatActivity).delegate.applyDayNight()
        }
    }

    private fun sortDialog(searchType: SearchType) {
        val options = resources.getStringArray(R.array.order_type)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.Sort_by)
            .setIcon(R.drawable.ic_action_sort_foreground)
            .setItems(options) { _, orderTypePosition ->
                preferences.edit().putInt(MainActivity.SORT_PREFERENCE, orderTypePosition).apply()

                when (orderTypePosition) {
                    OrderType.ASC.ordinal -> doWhenASC(searchType)
                    OrderType.DESC.ordinal -> doWhenDESC(searchType)
                }
            }
            .create().show()
    }

    private fun doWhenASC(searchType: SearchType){
        when (searchType) {
            SearchType.RATING -> movieViewModel?.sortByRatingAsc()
            SearchType.REALISE -> movieViewModel?.sortByRealiseAsc()
        }
        Snackbar.make(drawerLayout, R.string.Asc_order, Snackbar.LENGTH_LONG).show()
    }

    private fun doWhenDESC(searchType: SearchType){
        when (searchType) {
            SearchType.RATING -> movieViewModel?.sortByRatingDes()
            SearchType.REALISE -> movieViewModel?.sortByRealiseDes()
        }
        Snackbar.make(drawerLayout, R.string.Desc_order, Snackbar.LENGTH_LONG).show()
    }
}
