package com.example.tripapp2.presentation

import android.app.Application
import com.example.tripapp2.di.DaggerApplicationComponent

class TripApp: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}