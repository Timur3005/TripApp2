package com.example.tripapp2.domain

data class PlaceItem(
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
    val hasParking: Boolean
)
