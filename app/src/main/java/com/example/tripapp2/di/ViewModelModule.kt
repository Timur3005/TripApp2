package com.example.tripapp2.di

import androidx.lifecycle.ViewModel
import com.example.tripapp2.presentation.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(impl: HomeViewModel): ViewModel
}