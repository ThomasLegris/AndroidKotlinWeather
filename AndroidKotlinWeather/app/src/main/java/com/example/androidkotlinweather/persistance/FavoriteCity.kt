package com.example.androidkotlinweather.persistance

import androidx.room.Entity
import androidx.room.PrimaryKey

/// City Entity object to store in data base.
@Entity(tableName = "cities_table")
data class FavoriteCity(@PrimaryKey val name: String)
