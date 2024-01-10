package com.example.tripapp2.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommentsContainerDto(
    @SerializedName("results")
    @Expose
    val comments: List<CommentDto>
)
