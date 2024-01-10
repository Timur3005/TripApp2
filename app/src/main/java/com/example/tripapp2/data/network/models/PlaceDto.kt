package com.example.tripapp2.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PlaceDto(
        @SerializedName("id")
        @Expose
        val id: Int,
        @SerializedName("title")
        @Expose
        val title: String,
        @SerializedName("address")
        @Expose
        val address: String,
        @SerializedName("timetable")
        @Expose
        val timetable: String,
        @SerializedName("phone")
        @Expose
        val phone: String,
        @SerializedName("body_text")
        @Expose
        val bodyText: String,
        @SerializedName("description")
        @Expose
        val description: String,
        @SerializedName("foreign_url")
        @Expose
        val foreignUrl: String,
        @SerializedName("coords")
        @Expose
        val coords: CoordsDto,
        @SerializedName("subway")
        @Expose
        val subway: String,
        @SerializedName("images")
        @Expose
        val imageUrls: List<ImageDto>,
        @SerializedName("is_closed")
        @Expose
        val isClosed: Boolean,
        @SerializedName("categories")
        @Expose
        val categories: List<String>,
        @SerializedName("location")
        @Expose
        val location: String,
        @SerializedName("disable_comments")
        @Expose
        val disableComments: Boolean,
        @SerializedName("has_parking_lot")
        @Expose
        val hasParking: Boolean
    )