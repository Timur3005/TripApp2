package com.example.tripapp2.domain.entities

sealed class PlaceItemState{
    data class Place(
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
        val categories: List<Category>,
        val location: Cities,
        val disableComments: Boolean,
        val hasParking: Boolean,
        val inLiked: Boolean = false,
        val inRoute: Boolean = false
    ): PlaceItemState()
    data object Loading: PlaceItemState()
    data object Error: PlaceItemState()
    data object HttpError: PlaceItemState()
}
