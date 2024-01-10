package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import javax.inject.Inject

class GetLikedPlacesUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke() {
        repository.getLikedPlaces()
    }
}