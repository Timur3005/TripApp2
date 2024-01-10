package com.example.tripapp2.domain

import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.PlaceItemState
import kotlinx.coroutines.flow.Flow

interface ApplicationRepository {
    fun getShortPlaceItemList(filters: Filters)
    fun getPlaceItem(id: Int)
    fun getComments(id: Int)
    suspend fun saveLikedPlace(place: PlaceItemState.Place)
    suspend fun saveRoutePlace(place: PlaceItemState.Place)
    suspend fun deleteLikedPlace(place: PlaceItemState.Place)
    suspend fun deleteRoutePlace(place: PlaceItemState.Place)
    fun getLikedPlaces(): Flow<List<PlaceItemState.Place>>
    fun getRoutePlaces(): Flow<List<PlaceItemState.Place>>
}