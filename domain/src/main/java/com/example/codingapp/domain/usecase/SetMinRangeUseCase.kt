package com.example.codingapp.domain.usecase

import com.example.codingapp.domain.base.BaseUseCase
import com.example.codingapp.domain.exception.ErrorConverter
import com.example.codingapp.domain.repository.CryptoRepository
import kotlin.coroutines.CoroutineContext

class SetMinRangeUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CryptoRepository
) : BaseUseCase<SetMinRangeUseCase.Params, Unit>(context, converter) {

    override suspend fun executeOnBackground(params: Params) {
        repository.setMinRate(params.minRange)
    }

    data class Params(val minRange: Float)
}