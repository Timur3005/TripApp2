package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import javax.inject.Inject

class GetPlaceItemUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend fun responsePlaceItem(id: Int) {
        repository.responsePlaceItem(id)
    }

    fun getPlaceItemFlow() = repository.placeItemFlow
}