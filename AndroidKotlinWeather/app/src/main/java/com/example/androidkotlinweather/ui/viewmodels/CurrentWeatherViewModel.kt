package com.example.androidkotlinweather.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidkotlinweather.models.LocalWeatherResponse
import com.example.androidkotlinweather.models.ResultState
import com.example.androidkotlinweather.repositories.WeatherRepository
import kotlinx.coroutines.launch

class CurrentWeatherViewModel : ViewModel() {
    // Live data to update.
    private val _dataState: MutableLiveData<ResultState<LocalWeatherResponse>> = MutableLiveData()

    // Value of live data.
    val dataState: LiveData<ResultState<LocalWeatherResponse>>
        get() = _dataState

    fun requestWeather(cityName: String) {
        // Use scope to work on a separated thread.
        viewModelScope.launch {
            WeatherRepository.fetchWeather(cityName).collect { response ->
                /// Collect values emited by the repository and update livedata.
                _dataState.value = response
            }
        }
    }
}