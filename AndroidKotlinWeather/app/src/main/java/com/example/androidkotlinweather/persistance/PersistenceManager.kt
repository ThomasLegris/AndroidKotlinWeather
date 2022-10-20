package com.example.androidkotlinweather.persistance

import android.content.Context
import androidx.lifecycle.LiveData

/// Data base manager to persist and get data using the City Data base.
class PersistenceManager {
    /**
     * Singleton
     */
    companion object {
        /// Private Properties
        private var db: CityDataBase? = null

        /// Public Funcs
        fun sharedInstance(context: Context): PersistenceManager {
            db = CityDataBase.sharedInstance(context)
            return PersistenceManager()
        }
    }

    /**
     * Public Funcs
     */
    /// Inset a city object in database.
    suspend fun addCity(city: FavoriteCity) {
        db?.citiesDao()?.insert(city)
    }

    /// Update a city info in database.
    suspend fun updateCity(city: FavoriteCity) {
        db?.citiesDao()?.update(city)
    }

    /// Delete a city in the database.
    suspend fun deleteCity(city: FavoriteCity) {
        db?.citiesDao()?.delete(city)
    }

    /// Returns the value of favorite cities list 
    suspend fun cities(): List<FavoriteCity> {
        return db?.citiesDao()?.getAllCities() ?: listOf()
    }

    /// Live data which can be observed.
    fun citiesLiveData(): LiveData<List<FavoriteCity>>? {
        return db?.citiesDao()?.allCitiesLiveData()
    }
}