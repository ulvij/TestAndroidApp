package com.example.codingapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    fun observeBtcPrice(): Flow<Float>
    fun observeMinRate(): Flow<Float>
    fun observeMaxRate(): Flow<Float>

    suspend fun syncBtcPrice()

    suspend fun setMinRate(minPrice: Float)

    suspend fun setMaxRate(maxPrice: Float)

    suspend fun checkReachTargets(): Boolean

}