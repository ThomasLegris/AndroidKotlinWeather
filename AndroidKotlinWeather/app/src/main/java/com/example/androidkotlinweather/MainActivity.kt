package com.example.androidkotlinweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidkotlinweather.models.LocalWeatherResponse
import com.example.androidkotlinweather.models.WeatherGroup
import com.example.androidkotlinweather.models.WeatherService
import com.example.androidkotlinweather.models.getWeatherGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_view.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    /**
     ** Override Funcs
     **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    /**
     ** Private Funcs
     **/
    private fun initView() {
        city_request_button.setOnClickListener {
            didClickOnCityRequest()
        }
        setupDate()
    }

    /// Called when user has clicked on search button to find weather by its cityname.
    private fun didClickOnCityRequest() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiWeatherService = retrofit.create(WeatherService::class.java)

        apiWeatherService.getWeather(
            "5eb5a01a0f1829cf671e3fd56c7ccdc6",
            "metric",
            city_field_edit_text.text.toString()
        ).enqueue(object : Callback<LocalWeatherResponse> {
            override fun onResponse(
                call: Call<LocalWeatherResponse>,
                response: Response<LocalWeatherResponse>
            ) {
                response.body()?.let { data ->
                    main_weather_view.apply {
                        temp_text.text = data.main.temp.toString()
                        city_name_text.text = data.name
                        weather_description_text.text = data.weather?.first()?.main.toString()
                        weather_image_view.setImageResource(getWeatherGroup(data.weather?.first()?.identifier).imageRes)
                    }
                }
            }

            override fun onFailure(call: Call<LocalWeatherResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error occured at API call: $t")
            }
        }
        )
    }

    private fun setupDate() {
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatted = current.format(formatter)

        main_weather_view.date_text.text = formatted.toString()
    }
}