package com.example.tripapp2.data

import com.example.tripapp2.data.database.Dao
import com.example.tripapp2.data.network.ApiService
import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Category
import com.example.tripapp2.domain.entities.Cities
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.PlaceItemState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: Dao,
    private val mapper: PlacesAndCommentsMapper,
) : ApplicationRepository {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val responseShortPlaces = MutableSharedFlow<Filters>()

    @OptIn(FlowPreview::class)
    override val shortPlacesFlow = flow {
        emit(
            withContext(Dispatchers.IO) {
                mapper.mapShortListDtoContainerToShortPlaceItemEntity(
                    apiService.getShortPlacesList(
                        location = DEFAULT_FILTERS.location.urlCode,
                        categories = DEFAULT_FILTERS.category.joinToString(",") { it.slug }
                    )
                )
            }
        )
        responseShortPlaces.debounce(DELAY_BEFORE_RESPONSE).collect {
            if (it.query.isEmpty()) {
                emit(
                    withContext(Dispatchers.IO) {
                        mapper.mapShortListDtoContainerToShortPlaceItemEntity(
                            apiService.getShortPlacesList(
                                location = it.location.urlCode,
                                categories = it.category.joinToString(",") { item -> item.slug }
                            )
                        )
                    }
                )
            } else {
                emit(
                    withContext(Dispatchers.IO) {
                        mapper.mapShortListDtoContainerToShortPlaceItemEntity(
                            apiService.findPlace(
                                location = it.location.urlCode,
                                categories = it.category.joinToString(",") { item -> item.slug },
                                query = it.query
                            )
                        )
                    }
                )
            }
        }
    }.retry(RETRY_COUNT).stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun responseShortPlaceItemList(filters: Filters) {
        responseShortPlaces.emit(filters)
    }

    override val placeItemFlow = flow {
        responsePlaceItemFlow.collect {
            emit(
                withContext(Dispatchers.IO) {
                    mapper.mapPlaceDBModelToPlaceEntity(dao.getPlaceById(it))
                }
            )
        }
    }.catch {
        if (it is NullPointerException) {
            responsePlaceItemFlow.collect {
                emit(withContext(Dispatchers.IO)
                { mapper.mapPlaceDtoToPlaceEntity(apiService.getFullPlaceItem(it)) })
            }
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = null
    )
    private val responsePlaceItemFlow =
        MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun responsePlaceItem(id: Int) {
        responsePlaceItemFlow.emit(id)
    }

    private val responseComments =
        MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val commentsFlow = flow {
        responseComments.collect {
            emit(
                withContext(Dispatchers.IO) {
                    mapper.mapCommentsContainerToCommentEntityList(
                        apiService.getCommentsById(placeId = it)
                    )
                }
            )
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun responseComments(id: Int) {
        responseComments.emit(id)
    }

    override suspend fun saveLikedPlace(place: PlaceItemState.Place) {
        withContext(Dispatchers.IO) {
            try {
                dao.insertPlace(dao.getPlaceById(place.id).copy(inLiked = true))
            } catch (e: Exception) {
                dao.insertPlace(
                    mapper.mapPlaceEntityToPlaceDBModel(place).copy(inLiked = true)
                )
            }
        }
    }

    override suspend fun saveRoutePlace(place: PlaceItemState.Place) {
        withContext(Dispatchers.IO) {
            try {
                dao.insertPlace(dao.getPlaceById(place.id).copy(inRoute = true))
            } catch (e: Exception) {
                dao.insertPlace(
                    mapper.mapPlaceEntityToPlaceDBModel(place).copy(inRoute = true)
                )
            }
        }
    }

    override suspend fun deleteLikedPlace(place: PlaceItemState.Place) {
        withContext(Dispatchers.IO) {
            dao.updateLiked(place.id)
            dao.deletePlace(place.id)
        }
    }

    override suspend fun deleteRoutePlace(place: PlaceItemState.Place) {
        withContext(Dispatchers.IO) {
            dao.updateRoute(place.id)
            dao.deletePlace(place.id)
        }
    }

    override fun getLikedPlaces(): Flow<List<PlaceItemState.Place>> {
        return dao.getLikedPlaces().map {
            it.map { item ->
                mapper.mapPlaceDBModelToPlaceEntity(item)
            }
        }
    }

    override fun getRoutePlaces(): Flow<List<PlaceItemState.Place>> {
        return dao.getRoutePlaces().map {
            it.map { item ->
                mapper.mapPlaceDBModelToPlaceEntity(item)
            }
        }
    }

    companion object {
        private val DEFAULT_FILTERS = Filters(Cities.DEFAULT, listOf(Category.DEFAULT))
        private const val RETRY_COUNT = 3L
        private const val DELAY_BEFORE_RESPONSE = 200L
    }
}