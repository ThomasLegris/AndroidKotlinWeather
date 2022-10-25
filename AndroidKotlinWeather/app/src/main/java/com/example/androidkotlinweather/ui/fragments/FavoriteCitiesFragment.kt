package com.example.androidkotlinweather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidkotlinweather.R
import com.example.androidkotlinweather.models.CityWeather
import com.example.androidkotlinweather.persistance.PersistenceManager
import com.example.androidkotlinweather.ui.adapter.CityItemAdapter
import com.example.androidkotlinweather.ui.common.CustomPagerFragment
import com.example.androidkotlinweather.ui.viewmodels.FavoriteCitiesViewModel
import kotlinx.android.synthetic.main.fragment_favorite_cities.*
import kotlinx.coroutines.launch

/// Show favorite cities list.
class FavoriteCitiesFragment(title: String) : CustomPagerFragment(title), View.OnClickListener {
    /**
     * Private Properties
     */
    private lateinit var adapter: CityItemAdapter
    private lateinit var persistenceManager: PersistenceManager
    private var items: MutableList<CityWeather> = mutableListOf()
    private var viewModel: FavoriteCitiesViewModel = FavoriteCitiesViewModel()

    /**
     * Override Funcs
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    /**
     * Private Func
     */
    private fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = CityItemAdapter(items, this@FavoriteCitiesFragment)
        recycler_view.adapter = adapter
    }

    /// Sets up live data observer.
    private fun initViewModel() {
        this.context?.let {
            persistenceManager = PersistenceManager.sharedInstance(it)
            // Observes changes on database.
            persistenceManager.citiesLiveData()
                ?.observe(this.viewLifecycleOwner) {
                    items.clear()
                    fillDataSource()
                }
        }

        viewModel.dataState.observe(viewLifecycleOwner) { cities ->
            items.add(cities.last())
            adapter.notifyItemInserted(cities.size)
        }
    }

    private fun fillDataSource() {
        lifecycleScope.launch {
            //items = mutableListOf()
            val dataSource = persistenceManager.cities()
            empty_cities_text.visibility = if (dataSource.isEmpty()) View.VISIBLE else View.GONE
            recycler_view.visibility = if (dataSource.isEmpty()) View.GONE else View.VISIBLE

            viewModel.requestCitiesWeather(dataSource)
        }
    }

    override fun onClick(v: View?) {
        // TODO: To implement
    }
}