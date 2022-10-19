package com.example.androidkotlinweather.models
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    fun getWeather(@Query("appid") keyParam: String,
                   @Query("units") unitsParam: String,
                   @Query("q") cityParam: String): Call<LocalWeatherResponse>
}