package com.example.tripapp2.data

import com.example.tripapp2.data.database.PlaceDbModel
import com.example.tripapp2.data.network.models.CommentDto
import com.example.tripapp2.data.network.models.CommentsContainerDto
import com.example.tripapp2.data.network.models.ImageDto
import com.example.tripapp2.data.network.models.PlaceDto
import com.example.tripapp2.data.network.models.ShortPlaceItemDto
import com.example.tripapp2.data.network.models.ShortPlacesListContainerDto
import com.example.tripapp2.domain.entities.Category
import com.example.tripapp2.domain.entities.Cities
import com.example.tripapp2.domain.entities.PlaceItemCommentsState
import com.example.tripapp2.domain.entities.PlaceItemState
import com.example.tripapp2.domain.entities.ShortPlaceItem
import javax.inject.Inject

class PlacesAndCommentsMapper @Inject constructor() {

    private fun mapImageDtoListToStringList(imageDtoList: List<ImageDto>) =
        imageDtoList.map { it.imageUrl }

    private fun findCategoryObj(category: String) =
        enumValues<Category>().find { it.type == category } ?: Category.ATTRACTION

    private fun mapStringListToCategoryList(list: List<String>) = list.map {
        findCategoryObj(it)
    }

    private fun findCityObj(city: String) =
        enumValues<Cities>().find { it.urlCode == city }

    fun mapPlaceDtoToPlaceEntity(placeDto: PlaceDto) = PlaceItemState.Place(
        id = placeDto.id,
        title = placeDto.title,
        address = placeDto.address,
        timetable = placeDto.timetable,
        phone = placeDto.phone,
        bodyText = placeDto.bodyText,
        description = placeDto.description,
        lat = placeDto.coords.lat,
        lon = placeDto.coords.lon,
        foreignUrl = placeDto.foreignUrl,
        subway = placeDto.subway,
        imageUrls = mapImageDtoListToStringList(placeDto.imageUrls),
        isClosed = placeDto.isClosed,
        categories = mapStringListToCategoryList(placeDto.categories),
        location = findCityObj(placeDto.location) ?: Cities.MSK,
        disableComments = placeDto.disableComments,
        hasParking = placeDto.hasParking
    )

    private fun mapCommentDtoToCommentEntity(commentDto: CommentDto) =
        PlaceItemCommentsState.Comment(
            id = commentDto.id,
            postedDateInMillis = commentDto.datePosted.toLong(),
            text = commentDto.text,
            userName = commentDto.user.name,
            userPhotoUrl = commentDto.user.avatar,
            repliesCount = commentDto.repliesCount,
            threadInId = commentDto.thread,
            replyToId = commentDto.replyTo
        )

    fun mapCommentsContainerToCommentsEntity(commentsContainer: CommentsContainerDto):
            List<PlaceItemCommentsState.Comment>{
        return commentsContainer.comments.map {
            mapCommentDtoToCommentEntity(it)
        }
    }

    private fun mapShortPlaceItemToShortPlaceItemEntity(dto: ShortPlaceItemDto) = ShortPlaceItem(
        id = dto.id,
        title = dto.title
    )

    fun mapShortListDtoContainerToShortListEntity(dtoContainer: ShortPlacesListContainerDto) =
        dtoContainer.results.map {
            mapShortPlaceItemToShortPlaceItemEntity(it)
        }

    fun mapPlaceEntityToPlaceDBModel(place: PlaceItemState.Place) = PlaceDbModel(
        id = place.id,
        title = place.title,
        address = place.address,
        timetable = place.timetable,
        phone = place.phone,
        bodyText = place.bodyText,
        description = place.description,
        lat = place.lat,
        lon = place.lon,
        foreignUrl = place.foreignUrl,
        subway = place.subway,
        imageUrls = place.imageUrls.joinToString(),
        isClosed = place.isClosed,
        categories = place.categories,
        location = place.location,
        disableComments = place.disableComments,
        hasParking = place.hasParking
    )

    fun mapPlaceDBModelToPlaceEntity(place: PlaceDbModel) = PlaceItemState.Place(
        id = place.id,
        title = place.title,
        address = place.address,
        timetable = place.timetable,
        phone = place.phone,
        bodyText = place.bodyText,
        description = place.description,
        lat = place.lat,
        lon = place.lon,
        foreignUrl = place.foreignUrl,
        subway = place.subway,
        imageUrls = place.imageUrls.split(","),
        isClosed = place.isClosed,
        categories = place.categories,
        location = place.location,
        disableComments = place.disableComments,
        hasParking = place.hasParking
    )
}