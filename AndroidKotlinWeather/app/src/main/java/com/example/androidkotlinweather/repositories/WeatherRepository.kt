package com.example.androidkotlinweather.repositories

import com.example.androidkotlinweather.api.ApiManager
import com.example.androidkotlinweather.models.LocalWeatherResponse
import com.example.androidkotlinweather.models.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// TODO: Remove singleton and use DIP
object WeatherRepository {
    /**
     * Public Funcs
     */
    /// Call api manager fo fetch weather and emit data or error.
    /// Using Coroutines `flow` for asynchronous data stream.
    suspend fun fetchWeather(cityName: String): Flow<ResultState<LocalWeatherResponse>> = flow {
        emit(ResultState.Loading)
        // Create a flow of data.
        try {
            val weatherInfo = ApiManager.fetchWeather(cityName)
            emit(weatherInfo)
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }
}