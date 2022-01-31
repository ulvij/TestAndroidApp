package com.example.codingapp.data.local

import kotlinx.coroutines.flow.Flow

interface CryptoPreference {
    var minRate: Float
    var maxRate: Float
    var btcPrice: Float

    suspend fun cacheBtcPrice(price: Float)
    suspend fun cacheMinRate(price: Float)
    suspend fun cacheMaxRate(price: Float)
    fun observeBtcPrice(): Flow<Float>
    fun observeMinRate(): Flow<Float>
    fun observeMaxRate(): Flow<Float>
}