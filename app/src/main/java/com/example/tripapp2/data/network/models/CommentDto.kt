package com.example.tripapp2.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentDto(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("date_posted")
    @Expose
    val datePosted: Int,
    @SerializedName("text")
    @Expose
    val text: String,
    @SerializedName("user")
    @Expose
    val user: UserDto,
    @SerializedName("is_deleted")
    @Expose
    val isDeleted: Boolean,
    @SerializedName("replies_count")
    @Expose
    val repliesCount: Int,
    @SerializedName("thread")
    @Expose
    val thread: Int?,
    @SerializedName("reply_to")
    @Expose
    val replyTo: Int?
)