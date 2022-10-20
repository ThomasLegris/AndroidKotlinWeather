package com.example.androidkotlinweather.persistance

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
/// Interface to perform CRUD operations on Database.
interface FavoriteCityDataAccess {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: FavoriteCity)

    @Update
    suspend fun update(city: FavoriteCity)

    @Delete
    suspend fun delete(city: FavoriteCity)

    @Query("select * from cities_table order by name desc")
    suspend fun getAllCities(): List<FavoriteCity>

    @Query("select * from cities_table order by name desc")
    fun allCitiesLiveData(): LiveData<List<FavoriteCity>>
}