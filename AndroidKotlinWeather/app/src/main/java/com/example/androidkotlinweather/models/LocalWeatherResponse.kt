package com.example.androidkotlinweather.models

import com.google.gson.annotations.SerializedName

/// Parsed response object from the default city weather call.
data class LocalWeatherResponse(val name: String,
                                val main: MainField,
                                val weather: List<WeatherField>?)

data class WeatherField(@SerializedName("id") val identifier: Int,
                        val main: String,
                        val icon: String)

data class MainField(val temp: Float,
                     val pressure: Float,
                     val humidity: Float,
                     @SerializedName("temp_min") val tempMin: Float,
                     @SerializedName("temp_max") val tempMax: Float)