package com.example.tripapp2.data.network

import com.example.tripapp2.data.network.models.CommentsContainerDto
import com.example.tripapp2.data.network.models.PlaceDto
import com.example.tripapp2.data.network.models.ShortPlacesListContainerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("places/")
    suspend fun getShortPlacesList(
        @Query(LOCATION) location: String,
        @Query(TEXT_FORMAT) textFormat: String = TEXT_FORMAT_TEXT,
        @Query(CATEGORIES) categories: String,
        @Query(PAGE_SIZE) pageSize: Int = OUR_PAGE_SIZE
    ): ShortPlacesListContainerDto

    @GET("places/{place_id}/")
    suspend fun getFullPlaceItem(
        @Path(PLACE_ID) placeId: Int
    ): PlaceDto

    @GET("places/{place_id}/comments/")
    suspend fun getCommentsById(
        @Path(PLACE_ID) placeId: Int,
        @Query(PAGE_SIZE) pageSize: Int = OUR_PAGE_SIZE
    ): CommentsContainerDto

    @GET("search/")
    suspend fun findPlace(
        @Query(QUERY) query: String,
        @Query(LOCATION) location: String = EMPTY_QUERY,
        @Query(TEXT_FORMAT) textFormat: String = TEXT_FORMAT_TEXT,
        @Query(CATEGORIES) categories: String = EMPTY_QUERY,
        @Query(PAGE_SIZE) pageSize: Int = OUR_PAGE_SIZE,
        @Query(C_TYPE) cType: String = PLACE_TYPE
    ): ShortPlacesListContainerDto

    companion object{
        private const val PLACE_TYPE = "place"
        private const val C_TYPE = "ctype"
        private const val QUERY = "q"
        private const val PLACE_ID = "place_id"
        private const val LOCATION = "location"
        private const val TEXT_FORMAT = "text_format"
        private const val TEXT_FORMAT_TEXT = "text"
        private const val CATEGORIES = "categories"
        private const val PAGE_SIZE = "page_size"
        private const val OUR_PAGE_SIZE = 100
        private const val EMPTY_QUERY = ""
    }
}