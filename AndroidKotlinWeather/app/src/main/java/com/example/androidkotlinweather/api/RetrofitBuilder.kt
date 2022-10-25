package com.example.androidkotlinweather.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val owmUrl = "https://api.openweathermap.org/data/2.5/"

    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(owmUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiWeatherService: WeatherService = retrofit().create(WeatherService::class.java)
}