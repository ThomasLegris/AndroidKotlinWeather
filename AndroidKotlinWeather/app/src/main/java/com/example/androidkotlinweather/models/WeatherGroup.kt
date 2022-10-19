package com.example.androidkotlinweather.models

import com.example.androidkotlinweather.R

/**
 ** Interface
 **/
interface WeatherGroupInterface {
    val imageRes: Int
}

/**
 ** Enum
 **/
enum class WeatherGroup : WeatherGroupInterface {
    THUNDER {
        override val imageRes: Int = R.drawable.ic_thunder
    },
    DRIZZLE {
        override val imageRes: Int = R.drawable.ic_rain
    },
    RAIN {
        override val imageRes: Int = R.drawable.ic_rain
    },
    SNOW {
        override val imageRes: Int = R.drawable.ic_snow
    },
    ATMOSPHERE {
        override val imageRes: Int = R.drawable.ic_fog
    },
    CLEAR {
        override val imageRes: Int = R.drawable.ic_sun
    },
    CLOUDS {
        override val imageRes: Int = R.drawable.ic_sun_cloudy
    };
}

/**
 ** Helper Funcs
 **/
fun getWeatherGroup(identifier: Int?): WeatherGroup {
    return when (identifier) {
        in 200..232 -> WeatherGroup.THUNDER
        in 300..321 -> WeatherGroup.DRIZZLE
        in 500..531 -> WeatherGroup.RAIN
        in 600..622 -> WeatherGroup.SNOW
        in 701..781 -> WeatherGroup.ATMOSPHERE
        800 -> WeatherGroup.CLEAR
        else -> WeatherGroup.CLOUDS
    }
}