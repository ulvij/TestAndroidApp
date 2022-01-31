package com.example.codingapp.initializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
