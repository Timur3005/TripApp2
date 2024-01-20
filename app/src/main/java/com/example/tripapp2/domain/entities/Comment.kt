package com.example.tripapp2.domain.entities

data class Comment(
        val id: Int,
        val postedDateInMillis: Long,
        val text: String,
        val userName: String,
        val userPhotoUrl: String,
        val repliesCount: Int,
        val threadInId: Int?,
        val replyToId: Int?
    )