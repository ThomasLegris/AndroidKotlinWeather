package com.example.androidkotlinweather.fragments

import com.example.androidkotlinweather.fragments.adapter.CityItemAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidkotlinweather.R
import com.example.androidkotlinweather.api.ApiManager
import com.example.androidkotlinweather.fragments.common.CustomPagerFragment
import com.example.androidkotlinweather.models.CityWeather
import com.example.androidkotlinweather.models.getWeatherGroup
import com.example.androidkotlinweather.persistance.PersistenceManager
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
    }

    /**
     * Private Fun
     */
    private fun initView() {
        this.context?.let {
            persistenceManager = PersistenceManager.sharedInstance(it)
            persistenceManager.citiesLiveData()
                ?.observe(this.viewLifecycleOwner) {
                    items.clear()
                    fillDataSource()
                }
        }

        recycler_view.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        adapter = CityItemAdapter(items, this@FavoriteCitiesFragment)
        recycler_view.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fillDataSource() {
        lifecycleScope.launch {
            //items = mutableListOf()
            val dataSource = persistenceManager.cities()
            empty_cities_text.visibility = if (dataSource.isEmpty()) View.VISIBLE else View.GONE
            recycler_view.visibility = if (dataSource.isEmpty()) View.GONE else View.VISIBLE

            dataSource.forEach { favoriteCity ->
                ApiManager.requestWeather(favoriteCity.name) { response ->
                    val item = CityWeather(
                        response.name,
                        "${response.main.temp} °",
                        response.weather?.first()?.main.toString(),
                        getWeatherGroup(response.weather?.first()?.identifier).imageRes
                    )
                    items.add(item)
                    // Reload adapter datasource when all api calls has been done. Meaning that items list is full.
                    if (items.size == dataSource.size) {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        // TODO: To implement
    }
}