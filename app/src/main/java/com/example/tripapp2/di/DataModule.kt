package com.example.tripapp2.di

import android.content.Context
import com.example.tripapp2.data.database.PlacesDatabase
import com.example.tripapp2.data.network.ApiFactory
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideDao(context: Context) = PlacesDatabase.getInstance(context).getDao()

    @Provides
    fun provideApiService() = ApiFactory.apiService
}