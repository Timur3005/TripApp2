package com.example.tripapp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PlaceDbModel::class], version = 1, exportSchema = false)
@TypeConverters(EnumCitiesConverter::class, EnumCategoryListConverter::class)
abstract class PlacesDatabase: RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        private var db: PlacesDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context: Context): PlacesDatabase {
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context,
                        PlacesDatabase::class.java,
                        DB_NAME
                    ).build()
                db = instance
                return instance
            }
        }
    }
}