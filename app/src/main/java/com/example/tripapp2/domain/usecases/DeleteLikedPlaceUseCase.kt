package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.PlaceItemState
import javax.inject.Inject

class DeleteLikedPlaceUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(place: PlaceItemState.Place){
        repository.deleteLikedPlace(place)
    }
}