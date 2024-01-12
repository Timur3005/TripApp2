package com.example.tripapp2.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("avatar")
    @Expose
    val avatar: String
)