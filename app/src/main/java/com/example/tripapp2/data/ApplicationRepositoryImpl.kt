package com.example.tripapp2.data

import com.example.tripapp2.data.database.Dao
import com.example.tripapp2.data.network.ApiService
import com.example.tripapp2.domain.ApplicationRepository
import com.example.tripapp2.domain.entities.Category
import com.example.tripapp2.domain.entities.Cities
import com.example.tripapp2.domain.entities.Filters
import com.example.tripapp2.domain.entities.PlaceItemState
import com.example.tripapp2.domain.entities.ShortPlaceItemState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ApplicationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: Dao,
    private val mapper: PlacesAndCommentsMapper
): ApplicationRepository {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val responseShortPlaces = MutableSharedFlow<Filters>()

    val shortPlacesFlow = flow<ShortPlaceItemState>{
        emit(
            mapper.mapShortListDtoContainerToShortItemStateEntity(
                apiService.getShortPlacesList(
                    location = DEFAULT_FILTERS.location.urlCode,
                    categories = DEFAULT_FILTERS.category.joinToString(",")
                )
            )
        )
        responseShortPlaces.collect{
            emit(
                mapper.mapShortListDtoContainerToShortItemStateEntity(
                    apiService.getShortPlacesList(
                        location = it.location.urlCode,
                        categories = it.category.joinToString(",")
                    )
                )
            )
        }
    }.catch {
        emit(ShortPlaceItemState.Error)
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = ShortPlaceItemState.Loading
    )

    override suspend fun getShortPlaceItemList(filters: Filters) {
        responseShortPlaces.emit(filters)
    }

    private val _placeItemFlow = MutableStateFlow<PlaceItemState>(PlaceItemState.Loading)
    val placeItemFlow = _placeItemFlow.asSharedFlow()
    override suspend fun getPlaceItem(id: Int){
        try {
            _placeItemFlow.value = mapper
                .mapPlaceDBModelToPlaceEntity(dao.getPlaceById(id))
        }catch (e: Exception){
            try {
                _placeItemFlow.value = mapper
                    .mapPlaceDtoToPlaceEntity(apiService.getFullPlaceItem(id))
            }catch (e: Exception){
                _placeItemFlow.value = PlaceItemState.Error
            }
        }
    }

    override fun getComments(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun saveLikedPlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override suspend fun saveRoutePlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLikedPlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRoutePlace(place: PlaceItemState.Place) {
        TODO("Not yet implemented")
    }

    override fun getLikedPlaces(): Flow<List<PlaceItemState.Place>> {
        TODO("Not yet implemented")
    }

    override fun getRoutePlaces(): Flow<List<PlaceItemState.Place>> {
        TODO("Not yet implemented")
    }

    companion object{
        private val DEFAULT_FILTERS = Filters(Cities.DEFAULT, listOf(Category.DEFAULT))
    }
}