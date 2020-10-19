package com.example.films.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.films.R
import com.example.films.adapter.MovieRecycleAdapter
import com.example.films.util.ProgressState
import com.example.films.listener.AdapterListener
import com.example.films.model.Movie
import com.example.films.retrofit.MovieService
import com.example.films.util.ColorUtils.isDarkTheme
import com.example.films.util.Language
import com.example.films.util.OrderType
import com.example.films.util.SearchType
import com.example.films.viewmodel.MovieDataViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainFragment : Fragment(), AdapterListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    lateinit var movieDataViewModel: MovieDataViewModel
    private lateinit var movieAdapter: MovieRecycleAdapter
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieDataViewModel = MovieDataViewModel(requireActivity().application)
        initProgressBar()
        initRecyclerView()
        initSearchView()
        initSwitchNightMode()
        initDrawer()
        subscribeObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_container)
    }

    override fun onClickItem(movie: Movie) {
        val bundle = bundleOf("movie" to movie)
        navController.navigate(R.id.action_mainFragment_to_detailsFragment, bundle)

    }

    private fun initProgressBar() {
        circularProgressBar.circularRadius = 50
    }

    private fun initRecyclerView() {
        movie_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            movieAdapter =
                MovieRecycleAdapter(this@MainFragment)
            adapter = movieAdapter
        }
    }

    private fun subscribeObservers(){
        movieDataViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { error ->
            view?.let { Snackbar.make(it, error, Snackbar.LENGTH_LONG).show() }
        })

        movieDataViewModel.stateChangeLiveData.observe(
            viewLifecycleOwner,
            Observer { progressState ->
                setMovieView(progressState)
            })

        movieDataViewModel.movieDataLiveData.observe(viewLifecycleOwner, Observer { movies ->
            movieAdapter.submitList(movies)
        })
    }

    private fun initSearchView() {
        val ll: LinearLayout = searchview.getChildAt(0) as LinearLayout
        val ll2: LinearLayout = ll.getChildAt(2) as LinearLayout
        val ll3: LinearLayout = ll2.getChildAt(1) as LinearLayout
        val autoComplete = ll3.getChildAt(0) as SearchView.SearchAutoComplete
        autoComplete.setHintTextColor(ContextCompat.getColor(requireContext(), R.color.actionIcons))
        autoComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.actionIcons))
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (!searchview.isIconified) {
                    movieDataViewModel.search(text)
                }
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

        drawerLayout = requireActivity().findViewById(R.id.drawer_layout)
        navigationView = requireActivity().findViewById(R.id.navView)

        toggle =
            ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        preferences = requireActivity().getSharedPreferences(
            MainActivity.SEARCH_PREFERENCES,
            Context.MODE_PRIVATE
        )
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.Rating -> sortDialog(SearchType.RATING)
                R.id.Realise -> sortDialog(SearchType.REALISE)
                R.id.Language -> selectLanguage()
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun selectLanguage(){
        val options = resources.getStringArray(R.array.language)

        AlertDialog.Builder(requireContext())
            .setTitle("Select language")
            .setItems(options) { _, orderTypePosition ->
                preferences.edit().putInt(MainActivity.LANGUAGE_PREFERENCE, orderTypePosition).apply()

                when(orderTypePosition) {
                    Language.EN.ordinal -> movieDataViewModel.selectLanguage(Language.EN)
                    Language.HU.ordinal -> movieDataViewModel.selectLanguage(Language.HU)
                    Language.FR.ordinal -> movieDataViewModel.selectLanguage(Language.FR)
                }
            }.create().show()

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

    private fun doWhenASC(searchType: SearchType) {
        when (searchType) {
            SearchType.RATING -> movieDataViewModel.sortByRatingAsc()
            SearchType.REALISE -> movieDataViewModel.sortByRealiseAsc()
        }
        Snackbar.make(drawerLayout, R.string.Asc_order, Snackbar.LENGTH_LONG).show()
    }

    private fun doWhenDESC(searchType: SearchType) {
        when (searchType) {
            SearchType.RATING -> movieDataViewModel.sortByRatingDes()
            SearchType.REALISE -> movieDataViewModel.sortByRealiseDes()
        }
        Snackbar.make(drawerLayout, R.string.Desc_order, Snackbar.LENGTH_LONG).show()
    }

    private fun setMovieView(progressState: ProgressState) =
        when (progressState) {
            ProgressState.LOADING -> {
                movie_recycler_view.visibility = View.GONE
                empty_movie.visibility = View.GONE
                circularProgressBar.visibility = View.VISIBLE
            }
            ProgressState.EMPTY -> {
                movie_recycler_view.visibility = View.GONE
                empty_movie.visibility = View.VISIBLE
                circularProgressBar.visibility = View.GONE
            }
            ProgressState.FILLED -> {
                movie_recycler_view.visibility = View.VISIBLE
                empty_movie.visibility = View.GONE
                circularProgressBar.visibility = View.GONE
            }
        }
}
