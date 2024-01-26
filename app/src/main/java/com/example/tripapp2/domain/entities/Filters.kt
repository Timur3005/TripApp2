package com.example.tripapp2.domain.entities

data class Filters(
    val location: Cities,
    val category: List<Category>,
    val query: String = EMPTY_QUERY
){
    companion object{
        private const val EMPTY_QUERY = ""
    }
}
