package com.example.tripapp2.data

import com.example.tripapp2.data.database.Dao
import com.example.tripapp2.data.network.ApiService
import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Category
import com.example.tripapp2.domain.entities.Cities
import com.example.tripapp2.domain.entities.CommentsState
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.PlaceItemState
import com.example.tripapp2.domain.entities.ShortPlaceItemState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: Dao,
    private val mapper: PlacesAndCommentsMapper
): ApplicationRepository {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val responseShortPlaces = MutableSharedFlow<Filters>()

    override val shortPlacesFlow = flow {
        emit(
            mapper.mapShortListDtoContainerToShortItemStateEntity(
                apiService.getShortPlacesList(
                    location = DEFAULT_FILTERS.location.urlCode,
                    categories = DEFAULT_FILTERS.category.joinToString(",") { it.slug }
                )
            )
        )
        responseShortPlaces.collect {
            emit(ShortPlaceItemState.Loading)
            withContext(Dispatchers.IO){
                emit(
                    mapper.mapShortListDtoContainerToShortItemStateEntity(
                        apiService.getShortPlacesList(
                            location = it.location.urlCode,
                            categories = it.category.joinToString(","){item -> item.slug }
                        )
                    )
                )
            }
        }
    }.retry(RETRY_COUNT).catch{
        emit(ShortPlaceItemState.Error)
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = ShortPlaceItemState.Loading
    )

    override suspend fun getShortPlaceItemList(filters: Filters) {
        responseShortPlaces.emit(filters)
    }

    override val placeItemStateFlow = MutableStateFlow<PlaceItemState>(PlaceItemState.Loading)

    override suspend fun getPlaceItem(id: Int){
        try {
            placeItemStateFlow.value = mapper.mapPlaceDBModelToPlaceEntity(dao.getPlaceById(id))
        }catch (e: Exception){
            try {
                placeItemStateFlow.value =
                    mapper.mapPlaceDtoToPlaceEntity(apiService.getFullPlaceItem(id))
            }catch (e: Exception){
                placeItemStateFlow.value = PlaceItemState.Error
            }
        }
    }
    private val responseComments = MutableSharedFlow<Int>()
    override val commentsFlow = flow<CommentsState> {
        responseComments.collect{
            emit(
                mapper.mapCommentsContainerToCommentsEntity(
                    apiService.getCommentsById(placeId = it)
                )
            )
        }
    }.retry(RETRY_COUNT).catch {
        emit(CommentsState.Error)
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = CommentsState.Loading
    )

    override suspend fun getComments(id: Int) {
        responseComments.emit(id)
    }

    override suspend fun saveLikedPlace(place: PlaceItemState.Place) {
        try {
            dao.insertPlace(dao.getPlaceById(place.id).copy(inLiked = true))
        }catch (e: Exception){
            dao.insertPlace(
                mapper.mapPlaceEntityToPlaceDBModel(place).copy(inLiked = true)
            )
        }

    }

    override suspend fun saveRoutePlace(place: PlaceItemState.Place) {
        try {
            dao.insertPlace(dao.getPlaceById(place.id).copy(inRoute = true))
        } catch(e: Exception){
            dao.insertPlace(
                mapper.mapPlaceEntityToPlaceDBModel(place).copy(inRoute = true)
            )
        }
    }

    override suspend fun deleteLikedPlace(place: PlaceItemState.Place) {
        dao.updateLiked(place.id)
        dao.deletePlace(place.id)
    }

    override suspend fun deleteRoutePlace(place: PlaceItemState.Place) {
        dao.updateRoute(place.id)
        dao.deletePlace(place.id)
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

    companion object{
        private val DEFAULT_FILTERS = Filters(Cities.DEFAULT, listOf(Category.DEFAULT))
        private const val RETRY_COUNT = 3L
    }
}