package com.example.androidkotlinweather.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidkotlinweather.models.CityWeather
import com.example.androidkotlinweather.models.ResultState
import com.example.androidkotlinweather.models.getWeatherGroup
import com.example.androidkotlinweather.persistance.FavoriteCity
import com.example.androidkotlinweather.repositories.WeatherRepository
import kotlinx.coroutines.launch

class FavoriteCitiesViewModel : ViewModel() {
    // Live data which give a list of local weather response through a flow.
    private var _dataState: MutableLiveData<List<CityWeather>> =
        MutableLiveData()
    private var dataStateList: MutableList<CityWeather> = mutableListOf()

    val dataState: LiveData<List<CityWeather>>
        get() = _dataState

    fun requestCitiesWeather(cities: List<FavoriteCity>) {
        dataStateList.clear()
        viewModelScope.launch {
            cities.forEach { city ->
                WeatherRepository.fetchWeather(city.name).collect { resultState ->
                    when (resultState) {
                        is ResultState.Success -> {
                            val item = CityWeather(
                                resultState.data.name,
                                "${resultState.data.main.temp} °",
                                resultState.data.weather?.first()?.main.toString(),
                                getWeatherGroup(resultState.data.weather?.first()?.identifier).imageRes
                            )
                            dataStateList.add(item)
                            _dataState.value = dataStateList
                        }
                        is ResultState.Error -> {
                            // TODO: Handle error state
                            println("test list error ${resultState.exception}")
                        }
                        is ResultState.Loading -> {
                            // TODO: Handle loading state
                            println("test list loading")
                        }
                    }
                }
            }
        }
    }
}