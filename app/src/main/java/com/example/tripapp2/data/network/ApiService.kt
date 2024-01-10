package com.example.tripapp2.data.network

import com.example.tripapp2.data.network.models.PlaceDto
import com.example.tripapp2.data.network.models.ShortPlacesListContainerDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("places/" +
            "{?lang,fields,expand,order_by,text_format,ids," +
            "location,has_showings,showing_since,showing_until," +
            "categories,lon,lat,radius}")
    suspend fun getShortPlacesList(
        @Query(LOCATION) location: String,
        @Query(TEXT_FORMAT) textFormat: String = TEXT_FORMAT_TEXT,
        @Query(CATEGORIES) categories: String
    ): List<ShortPlacesListContainerDto>

    @GET("places/{place_id}/")
    suspend fun getFullPlaceItem(
        @Path(PLACE_ID) placeId: Int
    ): PlaceDto

    companion object{
        private const val PLACE_ID = "place_id"
        private const val LOCATION = "location"
        private const val TEXT_FORMAT = "text_format"
        private const val TEXT_FORMAT_TEXT = "text_format"
        private const val CATEGORIES = "categories"
    }
}