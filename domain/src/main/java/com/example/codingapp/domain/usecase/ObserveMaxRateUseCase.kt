package com.example.codingapp.domain.usecase

import com.example.codingapp.domain.base.BaseFlowUseCase
import com.example.codingapp.domain.exception.ErrorConverter
import com.example.codingapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class ObserveMaxRateUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CryptoRepository
) : BaseFlowUseCase<Unit, Float>(context, converter) {

    override fun createFlow(params: Unit): Flow<Float> {
        return repository.observeMaxRate()
    }
}