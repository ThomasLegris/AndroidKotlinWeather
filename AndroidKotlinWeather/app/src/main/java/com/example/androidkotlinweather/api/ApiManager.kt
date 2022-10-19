package com.example.androidkotlinweather.api

import android.util.Log
import com.example.androidkotlinweather.models.LocalWeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/// Singleton object to handle OpenWeatherMap api requests.
object ApiManager {
    /**
     * Private Properties
     */
    private const val apiKey = "5eb5a01a0f1829cf671e3fd56c7ccdc6"
    private const val units = "metric"
    private const val owmUrl = "https://api.openweathermap.org/data/2.5/"

    /**
     * Public Funcs
     */
    fun requestWeather(cityName: String, callback: (LocalWeatherResponse) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(owmUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiWeatherService = retrofit.create(WeatherService::class.java)

        apiWeatherService.getWeather(apiKey, units, cityName)
            .enqueue(object : Callback<LocalWeatherResponse> {
                override fun onResponse(
                    call: Call<LocalWeatherResponse>,
                    response: Response<LocalWeatherResponse>
                ) {
                    response.body()?.let { data ->
                        callback(data)
                    }
                }

                override fun onFailure(call: Call<LocalWeatherResponse>, t: Throwable) {
                    Log.e("API_ERROR", "Error occured at API call: $t")
                }
            })
    }
}