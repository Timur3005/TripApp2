package com.example.tripapp2.domain.entities

sealed class ShortPlaceItemState{
    data class ShortPlaceItemList(
        val list: List<ShortPlaceItem>
    ): ShortPlaceItemState()

    data object Loading: ShortPlaceItemState()
    data object Error: ShortPlaceItemState()
}