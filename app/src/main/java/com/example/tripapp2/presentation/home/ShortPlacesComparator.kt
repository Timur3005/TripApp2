package com.example.tripapp2.presentation.home

import androidx.recyclerview.widget.DiffUtil
import com.example.tripapp2.domain.entities.PlaceItemState
import com.example.tripapp2.domain.entities.ShortPlaceItem
import javax.inject.Inject

class ShortPlacesComparator @Inject constructor(): DiffUtil.ItemCallback<ShortPlaceItem>() {
    override fun areItemsTheSame(oldItem: ShortPlaceItem, newItem: ShortPlaceItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShortPlaceItem, newItem: ShortPlaceItem): Boolean {
        return oldItem == newItem
    }

}