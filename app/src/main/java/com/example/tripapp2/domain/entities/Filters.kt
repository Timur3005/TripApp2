package com.example.tripapp2.domain.entities

data class Filters(
    val location: Cities,
    val category: List<Category>
)
