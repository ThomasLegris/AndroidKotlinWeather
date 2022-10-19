package com.example.androidkotlinweather.prefs

import android.content.Context
import android.content.SharedPreferences

/// Singleton to managed shared preferences.
object SharedPrefManager {
    /**
     * Private Properties
     */
    private const val DEFAULT_WEATHER_PREFS: String = "DEFAULT_WEATHER_PREFS"
    private const val CITY_NAME: String = "CITY_NAME"

    /**
     * Public Funcs
     */
    /// Register last city searched in preferences.
    fun updateLastCity(context: Context, city: String) {
        val prefs: SharedPreferences = sharedPref(context)
        val editor = prefs.edit()
        editor.putString(CITY_NAME, city)
        editor.apply()
    }

    /// Retrieves last city registered in preferences.
    fun getLastCity(context: Context): String {
        val prefs: SharedPreferences = sharedPref(context)
        return prefs.getString(CITY_NAME, "") ?: ""
    }

    /**
     * Private Funcs
     */
    /// Returns a shared preferences instance with a custom preference name.
    private fun sharedPref(
        context: Context,
        prefName: String = DEFAULT_WEATHER_PREFS
    ): SharedPreferences {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }
}