package com.example.codingapp.di

import com.example.codingapp.initializers.AppInitializer
import com.example.codingapp.initializers.AppInitializers
import com.example.codingapp.initializers.TimberInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideTimberInitializer(): TimberInitializer {
        return TimberInitializer()
    }

    @Provides
    fun provideAppInitializer(timberInitializer: TimberInitializer): AppInitializer {
        return AppInitializers(timberInitializer)
    }

}