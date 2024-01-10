package com.example.tripapp2.data

import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.PlaceItemState
import kotlinx.coroutines.flow.Flow

class ApplicationRepositoryImpl: ApplicationRepository {
    override fun getShortPlaceItemList(filters: Filters) {
        TODO("Not yet implemented")
    }

    override fun getPlaceItem(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getComments(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun saveLikedPlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override suspend fun saveRoutePlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLikedPlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRoutePlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override fun getLikedPlaces(): Flow<List<PlaceItemState.Place>> {
        TODO("Not yet implemented")
    }

    override fun getRoutePlaces(): Flow<List<PlaceItemState.Place>> {
        TODO("Not yet implemented")
    }
}