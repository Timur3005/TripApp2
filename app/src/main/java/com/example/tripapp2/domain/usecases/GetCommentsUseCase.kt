package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend fun responseComments(id: Int){
        repository.responseComments(id)
    }

    fun getCommentsFlow() = repository.commentsFlow

}