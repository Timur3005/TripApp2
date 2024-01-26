package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Filters
import javax.inject.Inject

class GetShortItemListUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend fun responseShortItems(filters: Filters) {
        repository.responseShortPlaceItemList(filters)
    }

    fun getShortItems() = repository.shortPlacesFlow
}