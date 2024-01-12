package com.example.tripapp2.domain.entities

sealed class PlaceItemCommentsState {
    data class Comment(
        val id: Int,
        val postedDateInMillis: Long,
        val text: String,
        val userName: String,
        val userPhotoUrl: String,
        val repliesCount: Int,
        val threadInId: Int?,
        val replyToId: Int?
    ): PlaceItemCommentsState()

    data object Loading: PlaceItemState()
    data object Error: PlaceItemState()
}