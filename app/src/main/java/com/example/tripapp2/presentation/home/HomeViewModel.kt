package com.example.tripapp2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.usecases.GetShortItemListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getShortItemListUseCase: GetShortItemListUseCase,
    private val repository: ApplicationRepository
): ViewModel() {
    fun getPlaceWithFilters(filters: Filters) =
        viewModelScope.launch { getShortItemListUseCase(filters) }

    val placesFlow = repository.shortPlacesFlow
}