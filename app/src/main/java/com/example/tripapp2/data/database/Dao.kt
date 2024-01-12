package com.example.tripapp2.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(placeDbModel: PlaceDbModel)
    @Query("SELECT * FROM places WHERE inLiked == 1")
    fun getLikedPlaces(): Flow<List<PlaceDbModel>>
    @Query("SELECT * FROM places WHERE inRoute == 1")
    fun getRoutePlaces(): Flow<List<PlaceDbModel>>
    @Query("SELECT * FROM places WHERE id = :id LIMIT 1")
    suspend fun getPlaceById(id: Int): PlaceDbModel
    @Query("UPDATE places SET inLiked = NOT inLiked WHERE id = :id")
    suspend fun updateLiked(id: Int)
    @Query("UPDATE places SET inRoute = NOT inRoute WHERE id = :id")
    suspend fun updateRoute(id: Int)
}