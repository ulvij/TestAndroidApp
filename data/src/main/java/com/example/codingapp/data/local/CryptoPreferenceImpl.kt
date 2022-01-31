package com.example.codingapp.data.local

import android.content.Context
import com.example.codingapp.data.local.base.BasePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CryptoPreferenceImpl(
    context: Context
) : BasePreferences(context), CryptoPreference {

    override val filename: String
        get() = "crypto_preference"

    private var _minRate = MutableStateFlow(0.0F)
    override var minRate by FloatValue("min_rate", 0.0F)

    private var _maxRate = MutableStateFlow(0.0F)
    override var maxRate by FloatValue("max_rate", 0.0F)

    private var _btcPrice = MutableStateFlow(0.0F)
    override var btcPrice by FloatValue("btc_price", 0.0F)

    init {
        _btcPrice.value = btcPrice
        _minRate.value = minRate
        _maxRate.value = maxRate
    }

    override suspend fun cacheMinRate(price: Float) {
        minRate = price
        _minRate.emit(price)
    }

    override suspend fun cacheMaxRate(price: Float) {
        maxRate = price
        _maxRate.emit(price)
    }

    override suspend fun cacheBtcPrice(price: Float) {
        btcPrice = price
        _btcPrice.emit(price)
    }

    override fun observeBtcPrice(): Flow<Float> {
        return _btcPrice.asStateFlow()
    }

    override fun observeMinRate(): Flow<Float> {
        return _minRate.asStateFlow()
    }

    override fun observeMaxRate(): Flow<Float> {
        return _maxRate.asStateFlow()
    }
}