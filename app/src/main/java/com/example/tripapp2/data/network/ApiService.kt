package com.example.tripapp2.data.network

import com.example.tripapp2.data.network.models.CommentsContainerDto
import com.example.tripapp2.data.network.models.PlaceDto
import com.example.tripapp2.data.network.models.ShortPlacesListContainerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("places/" +
            "{?lang,fields,expand,order_by,text_format,ids," +
            "location,has_showings,showing_since,showing_until," +
            "categories,lon,lat,radius,page_size}")
    suspend fun getShortPlacesList(
        @Query(LOCATION) location: String,
        @Query(TEXT_FORMAT) textFormat: String = TEXT_FORMAT_TEXT,
        @Query(CATEGORIES) categories: String,
        @Query(PAGE_SIZE) pageSize: Int = OUR_PAGE_SIZE
    ): List<ShortPlacesListContainerDto>

    @GET("places/{place_id}/")
    suspend fun getFullPlaceItem(
        @Path(PLACE_ID) placeId: Int
    ): PlaceDto

    @GET("places/{place_id}/comments/{?lang,fields,order_by,ids,page_size}")
    suspend fun getCommentsById(
        @Path(PLACE_ID) id: Int,
        @Query(PAGE_SIZE) pageSize: Int = OUR_PAGE_SIZE
    ): CommentsContainerDto

    companion object{
        private const val PLACE_ID = "place_id"
        private const val LOCATION = "location"
        private const val TEXT_FORMAT = "text_format"
        private const val TEXT_FORMAT_TEXT = "text_format"
        private const val CATEGORIES = "categories"
        private const val PAGE_SIZE = "page_size"
        private const val OUR_PAGE_SIZE = 100
    }
}