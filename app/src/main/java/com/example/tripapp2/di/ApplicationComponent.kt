package com.example.tripapp2.di

import android.content.Context
import com.example.tripapp2.presentation.MainActivity
import com.example.tripapp2.presentation.home.HomeFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        DomainModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(fragment: HomeFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}