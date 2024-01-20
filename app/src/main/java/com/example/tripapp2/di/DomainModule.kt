package com.example.tripapp2.di

import com.example.tripapp2.data.ApplicationRepositoryImpl
import com.example.tripapp2.domain.ApplicationRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindRepository(impl: ApplicationRepositoryImpl): ApplicationRepository
}