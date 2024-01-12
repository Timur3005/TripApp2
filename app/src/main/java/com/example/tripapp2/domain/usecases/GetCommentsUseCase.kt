package com.example.tripapp2.domain.usecases

import com.example.tripapp2.domain.ApplicationRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: ApplicationRepository
) {
    suspend operator fun invoke(id: Int){
        repository.getComments(id)
    }

}