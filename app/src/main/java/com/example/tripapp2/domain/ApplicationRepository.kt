package com.example.tripapp2.domain

import com.example.tripapp2.domain.entities.CommentsState
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.PlaceItemState
import com.example.tripapp2.domain.entities.ShortPlaceItemState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ApplicationRepository {
    val commentsFlow: StateFlow<CommentsState>
    val shortPlacesFlow: StateFlow<ShortPlaceItemState>
    val placeItemStateFlow: StateFlow<PlaceItemState>
    suspend fun getShortPlaceItemList(filters: Filters)
    suspend fun getPlaceItem(id: Int)
    suspend fun getComments(id: Int)
    suspend fun saveLikedPlace(place: PlaceItemState.Place)
    suspend fun saveRoutePlace(place: PlaceItemState.Place)
    suspend fun deleteLikedPlace(place: PlaceItemState.Place)
    suspend fun deleteRoutePlace(place: PlaceItemState.Place)
    fun getLikedPlaces(): Flow<List<PlaceItemState.Place>>
    fun getRoutePlaces(): Flow<List<PlaceItemState.Place>>
}