package com.example.androidkotlinweather.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteCity::class],
    version = 1, // To update when there is a change in db schema.
    exportSchema = true // Needed to allowed automatic migration.
)
/// Room Data base singleton.
abstract class CityDataBase : RoomDatabase() {
    /**
     * Public Funcs
     */
    /// The data access object used to perform operatiobs on DB.
    abstract fun citiesDao(): FavoriteCityDataAccess

    /**
     * Singleton
     */
    companion object {
        @Volatile
        private var instance: CityDataBase? = null

        @Synchronized
        fun sharedInstance(context: Context): CityDataBase? {
            val dbInstance = Room.databaseBuilder(
                context.applicationContext,
                CityDataBase::class.java,
                "city_database"
            ).build()

            return dbInstance
        }
    }
}