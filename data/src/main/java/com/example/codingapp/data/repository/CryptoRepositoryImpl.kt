package com.example.codingapp.data.repository

import com.example.codingapp.data.local.CryptoPreference
import com.example.codingapp.data.remote.CryptoApi
import com.example.codingapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow

class CryptoRepositoryImpl(
    private val cryptoApi: CryptoApi,
    private val cryptoPreference: CryptoPreference,
) : CryptoRepository {

    override fun observeBtcPrice(): Flow<Float> {
        return cryptoPreference.observeBtcPrice()
    }

    override fun observeMaxRate(): Flow<Float> {
        return cryptoPreference.observeMaxRate()
    }

    override fun observeMinRate(): Flow<Float> {
        return cryptoPreference.observeMinRate()
    }

    override suspend fun syncBtcPrice() {
        val lastPrice = cryptoApi.getBtcCurrentPrice()
        val priceForUsd = lastPrice.bpi.USD.rate_float
        cryptoPreference.cacheBtcPrice(priceForUsd)
    }

    override suspend fun setMinRate(minPrice: Float) {
        cryptoPreference.cacheMinRate(minPrice)
    }

    override suspend fun setMaxRate(maxPrice: Float) {
        cryptoPreference.cacheMaxRate(maxPrice)
    }

    override suspend fun checkReachTargets(): Boolean {
        val currentPrice = cryptoPreference.btcPrice
        val minPrice = cryptoPreference.minRate
        val maxPrice = cryptoPreference.maxRate
        return currentPrice !in minPrice..maxPrice // just simple logic to handle for a few cases, does not work for all cases
    }


}