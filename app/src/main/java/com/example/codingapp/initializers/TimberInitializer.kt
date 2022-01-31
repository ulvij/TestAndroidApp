package com.example.codingapp.initializers

import android.app.Application
import com.example.codingapp.BuildConfig
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer : AppInitializer {

    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                // Adds the line number to the tag
                override fun createStackElementTag(element: StackTraceElement) =
                    "${super.createStackElementTag(element)}:${element.lineNumber}"
            })
        }
    }
}
