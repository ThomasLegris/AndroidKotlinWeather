package com.example.androidkotlinweather.api

import com.example.androidkotlinweather.models.LocalWeatherResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 ** API Service Interface
 **/
interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("appid") keyParam: String,
        @Query("units") unitsParam: String,
        @Query("q") cityParam: String
    ): Response<LocalWeatherResponse>
}