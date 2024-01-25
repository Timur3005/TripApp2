package com.example.tripapp2.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.tripapp2.databinding.PlaceItemBinding
import com.example.tripapp2.domain.entities.ShortPlaceItem
import javax.inject.Inject

class PlacesAdapter @Inject constructor(
    comparator: ShortPlacesComparator
): ListAdapter<ShortPlaceItem, PlacesViewHolder>(comparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        return PlacesViewHolder(
            PlaceItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        val placeItem = getItem(position)
        holder.binding.tvItemName.text = placeItem.title
    }

}