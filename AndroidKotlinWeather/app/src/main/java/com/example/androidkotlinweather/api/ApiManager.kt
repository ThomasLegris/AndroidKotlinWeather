package com.example.androidkotlinweather.api

import com.example.androidkotlinweather.api.RetrofitBuilder.apiWeatherService
import com.example.androidkotlinweather.models.LocalWeatherResponse
import com.example.androidkotlinweather.models.ResultState

/// Singleton object to handle OpenWeatherMap api requests.
object ApiManager {
    /**
     * Private Properties
     */
    private const val apiKey = "5eb5a01a0f1829cf671e3fd56c7ccdc6"
    private const val units = "metric"

    /**
     * Public Funcs
     */
    suspend fun fetchWeather(cityName: String): ResultState<LocalWeatherResponse> {
        val result = apiWeatherService.getWeather(apiKey, units, cityName)

        return if (result.isSuccessful && result.body() != null) {
            ResultState.Success(result.body()!!)
        } else {
            ResultState.Error(Exception(result.errorBody()?.string()))
        }
    }
}