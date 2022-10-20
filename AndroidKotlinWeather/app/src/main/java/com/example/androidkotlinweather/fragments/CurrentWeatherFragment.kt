package com.example.androidkotlinweather.fragments

import ViewPagerFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidkotlinweather.R
import com.example.androidkotlinweather.api.ApiManager
import com.example.androidkotlinweather.models.LocalWeatherResponse
import com.example.androidkotlinweather.models.getWeatherGroup
import com.example.androidkotlinweather.prefs.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.android.synthetic.main.main_view.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/// Screen to show weather by city name.
class CurrentWeatherFragment(title: String) : ViewPagerFragment(title) {
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
        city_request_button.setOnClickListener {
            didClickOnCityRequest()
        }

        this.context?.let {
            ApiManager.requestWeather(SharedPrefManager.getLastCity(it)) { response ->
                updateView(response)
            }
        }

        setupDate()
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
}