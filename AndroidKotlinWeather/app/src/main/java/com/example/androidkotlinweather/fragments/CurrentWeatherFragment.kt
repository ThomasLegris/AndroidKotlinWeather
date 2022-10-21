package com.example.androidkotlinweather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.androidkotlinweather.R
import com.example.androidkotlinweather.api.ApiManager
import com.example.androidkotlinweather.fragments.common.CustomPagerFragment
import com.example.androidkotlinweather.models.LocalWeatherResponse
import com.example.androidkotlinweather.models.getWeatherGroup
import com.example.androidkotlinweather.persistance.FavoriteCity
import com.example.androidkotlinweather.persistance.PersistenceManager
import com.example.androidkotlinweather.prefs.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.main_view.view.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/// Screen to show weather by city name.
class CurrentWeatherFragment(title: String) : CustomPagerFragment(title) {
    /**
     * Private Properties
     **/
    private lateinit var persistenceManager: PersistenceManager

    /**
     * Override Funcs
     **/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    /**
     * Private Funcs
     **/
    private fun initView() {
        this.context?.let {
            persistenceManager = PersistenceManager.sharedInstance(it)
        }

        city_request_button.setOnClickListener {
            didClickOnCityRequest()
        }

        favorite_button.setOnClickListener {
            addOrRemoveToFavorite()
            updateFavoriteImage()
        }

        this.context?.let {
            ApiManager.requestWeather(SharedPrefManager.getLastCity(it)) { response ->
                updateView(response)
            }
        }

        setupDate()
        updateFavoriteImage()
    }

    /// Called when user has clicked on search button to find weather by its cityname.
    private fun didClickOnCityRequest() {
        ApiManager.requestWeather(city_field_edit_text.text.toString()) { response ->
            updateView(
                response
            )
        }
    }

    private fun updateView(response: LocalWeatherResponse) {
        main_weather_view.apply {
            temp_text.text = response.main.temp.toString()
            city_name_text.text = response.name
            weather_description_text.text = response.weather?.first()?.main.toString()
            weather_image_view.setImageResource(getWeatherGroup(response.weather?.first()?.identifier).imageRes)
        }

        updateFavoriteImage()

        this.context?.let {
            /// Save this city in shared pref.
            SharedPrefManager.updateLastCity(it, response.name)
        }
    }

    /// Setting up the current date time.
    private fun setupDate() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        main_weather_view.date_text.text = formatted.toString()
    }

    /// Update a city by adding it or removing it in favorites list.
    private fun addOrRemoveToFavorite() {
        val name = city_name_text.text.toString()
        if (name.isNotEmpty()) {
            lifecycleScope.launch {
                val city = FavoriteCity(name)
                if (persistenceManager.cities().contains(city)) {
                    persistenceManager.deleteCity(city)
                } else {
                    persistenceManager.addCity(city)
                }
                updateFavoriteImage()
            }
        }
    }

    private fun updateFavoriteImage() {
        lifecycleScope.launch {
            if (persistenceManager.cities()
                    .contains(FavoriteCity(city_name_text.text.toString()))
            ) {
                favorite_button.setImageResource(R.drawable.ic_favorite_on)
            } else {
                favorite_button.setImageResource(R.drawable.ic_favorite_off)
            }
        }
    }
}