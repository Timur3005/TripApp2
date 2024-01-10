package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Filters
import javax.inject.Inject

class GetShortItemListUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    operator fun invoke(filters: Filters) {
        repository.getShortPlaceItemList(filters)
    }
}