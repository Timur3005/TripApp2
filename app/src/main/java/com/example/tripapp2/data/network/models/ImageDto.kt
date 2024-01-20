package com.example.tripapp2.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("image")
    @Expose
    val imageUrl: String
)