package com.example.androidkotlinweather.models

/// Object which represent the weather for a selected city.
data class CityWeather(
    val name: String,
    val temp: String,
    val description: String,
    val imageRes: Int
)
