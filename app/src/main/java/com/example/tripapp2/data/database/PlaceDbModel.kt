package com.example.tripapp2.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tripapp2.domain.entities.Category
import com.example.tripapp2.domain.entities.Cities
@Entity(tableName = "places")
data class PlaceDbModel(
        @PrimaryKey
        val id: Int,
        val title: String,
        val address: String,
        val timetable: String,
        val phone: String,
        val bodyText: String,
        val description: String,
        val foreignUrl: String,
        val lat: Double,
        val lon: Double,
        val subway: String,
        val imageUrls: List<String>,
        val isClosed: Boolean,
        val categories: Category,
        val location: Cities,
        val disableComments: Boolean,
        val hasParking: Boolean,
        val inLiked: Boolean = false,
        val inRoute: Boolean = false
)