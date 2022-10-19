package com.example.androidkotlinweather

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidkotlinweather.api.ApiManager.requestWeather
import com.example.androidkotlinweather.models.LocalWeatherResponse
import com.example.androidkotlinweather.prefs.SharedPrefManager
import com.example.androidkotlinweather.api.WeatherService
import com.example.androidkotlinweather.models.getWeatherGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_view.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/// Main entry of the app.
/// Screen used to search city by its name.
class MainActivity : AppCompatActivity() {
    /**
     * Override Funcs
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    /**
     * Private Funcs
     **/
    private fun initView() {
        city_request_button.setOnClickListener {
            didClickOnCityRequest()
        }
        requestWeather(SharedPrefManager.getLastCity(this)) { response -> updateView(response)}
        setupDate()
    }


    /// Called when user has clicked on search button to find weather by its cityname.
    private fun didClickOnCityRequest() {
        requestWeather(city_field_edit_text.text.toString()) { response -> updateView(response)}
    }

    private fun updateView(response: LocalWeatherResponse) {
        main_weather_view.apply {
            temp_text.text = response.main.temp.toString()
            city_name_text.text = response.name
            weather_description_text.text = response.weather?.first()?.main.toString()
            weather_image_view.setImageResource(getWeatherGroup(response.weather?.first()?.identifier).imageRes)
        }

        /// Save this city in shared pref.
        SharedPrefManager.updateLastCity(this@MainActivity, response.name)
    }

    /// Setting up the current date time.
    private fun setupDate() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        main_weather_view.date_text.text = formatted.toString()
    }
}