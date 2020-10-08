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
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betterfilms.R
import com.example.betterfilms.adapter.MovieRecycleAdapter
import com.example.betterfilms.listener.AdapterListener
import com.example.betterfilms.model.Movie
import com.example.betterfilms.util.ColorUtil.isDarkTheme
import com.example.betterfilms.util.OrderType
import com.example.betterfilms.util.SearchType
import com.example.betterfilms.viewmodel.MovieDataViewModel
import com.example.films.R
import com.example.films.adapter.MovieRecycleAdapter
import com.example.films.listener.AdapterListener
import com.example.films.model.Movie
import com.example.films.util.OrderType
import com.example.films.util.SearchType
import com.example.films.viewmodel.MovieDataViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.toolbar_main.*

class MainFragment : Fragment(), AdapterListener {

    private lateinit var drawerLayout:DrawerLayout
    private  lateinit var navigationView: NavigationView
    lateinit var navController: NavController
    lateinit var movieDataViewModel: MovieDataViewModel
    private lateinit var movieAdapter: MovieRecycleAdapter
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var preferences: SharedPreferences

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieDataViewModel = MovieDataViewModel(requireActivity().application)
        initRecyclerView()
        initSearchView()
        initSwitchNightMode()
        initDrawer()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_container)

    }

    override fun onClickItem(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
        navController.navigate(R.id.action_mainFragment_to_detailsFragment, bundle)

    }

    private fun initRecyclerView() {
        movie_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            movieAdapter =
                MovieRecycleAdapter(this@MainFragment)
            adapter = movieAdapter
        }

        movieDataViewModel.movieDataLiveData.observe(viewLifecycleOwner, Observer { movies ->
            movieAdapter.submitList(movies)
        })
    }

    private fun initSearchView() {
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                movieDataViewModel.search(text)
                return false
            }
        })
    }

    private fun initSwitchNightMode() {
        bt_night.setOnClickListener {
            val isNightMode: Boolean = isDarkTheme(requireActivity())
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

    private fun initDrawer() {

        drawerLayout =  requireActivity().findViewById(R.id.drawer_layout)
        navigationView =  requireActivity().findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        preferences = requireActivity().getSharedPreferences(MainActivity.SEARCH_PREFERENCES, Context.MODE_PRIVATE)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> sortDialog(SearchType.RATING)
                R.id.Realise -> sortDialog(SearchType.REALISE)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun sortDialog(searchType: SearchType) {
        val options = resources.getStringArray(R.array.order_type)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.Sort_by)
            .setIcon(R.drawable.ic_sort)
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
            SearchType.RATING -> movieDataViewModel.sortByRatingAsc()
            SearchType.REALISE -> movieDataViewModel.sortByRealiseAsc()
        }
        Snackbar.make(drawerLayout, R.string.Asc_order, Snackbar.LENGTH_LONG).show()
    }

    private fun doWhenDESC(searchType: SearchType){
        when (searchType) {
            SearchType.RATING -> movieDataViewModel.sortByRatingDes()
            SearchType.REALISE -> movieDataViewModel.sortByRealiseDes()
        }
        Snackbar.make(drawerLayout, R.string.Desc_order, Snackbar.LENGTH_LONG).show()
    }
}
