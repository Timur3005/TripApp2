package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import javax.inject.Inject

class GetRoutePlacesUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke() {
        repository.getRoutePlaces()
    }
}