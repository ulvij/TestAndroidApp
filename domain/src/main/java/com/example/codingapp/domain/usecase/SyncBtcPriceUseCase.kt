package com.example.codingapp.domain.usecase

import com.example.codingapp.domain.base.BaseUseCase
import com.example.codingapp.domain.exception.ErrorConverter
import com.example.codingapp.domain.repository.CryptoRepository
import kotlin.coroutines.CoroutineContext

class SyncBtcPriceUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CryptoRepository
) : BaseUseCase<Unit, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Unit) {
        repository.syncBtcPrice()
    }
}