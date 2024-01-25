package com.example.tripapp2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.ShortPlaceItemState
import com.example.tripapp2.domain.usecases.GetShortItemListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getShortItemListUseCase: GetShortItemListUseCase
): ViewModel() {
    fun getPlaceWithFilters(filters: Filters) = viewModelScope.launch {
            getShortItemListUseCase.responseShortItems(filters)
    }

    val placesFlow = getShortItemListUseCase.getShortItems()
        .map { ShortPlaceItemState.ShortPlaceItemList(it) as ShortPlaceItemState}
        .onStart { emit(ShortPlaceItemState.Loading) }
        .catch { emit(ShortPlaceItemState.Error) }

}