package com.example.codingapp.domain.usecase

import com.example.codingapp.domain.base.BaseUseCase
import com.example.codingapp.domain.exception.ErrorConverter
import com.example.codingapp.domain.repository.CryptoRepository
import kotlin.coroutines.CoroutineContext

class CheckReachTargetsUseCase(
    context: CoroutineContext,
    converter: ErrorConverter,
    private val repository: CryptoRepository
) : BaseUseCase<Unit, Boolean>(context, converter) {

    override suspend fun executeOnBackground(params: Unit): Boolean {
        return repository.checkReachTargets()
    }
}