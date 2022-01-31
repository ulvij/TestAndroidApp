package com.example.codingapp.domain.di

import com.example.codingapp.domain.exception.ErrorConverter
import com.example.codingapp.domain.exception.ErrorConverterImpl
import com.example.codingapp.domain.exception.ErrorMapper
import com.example.codingapp.domain.repository.CryptoRepository
import com.example.codingapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import kotlin.coroutines.CoroutineContext


const val IO_CONTEXT = "IO_CONTEXT"
const val ERROR_MAPPER_NETWORK = "ERROR_MAPPER_NETWORK"

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideErrorConverter(@Named(ERROR_MAPPER_NETWORK) errorMapper: ErrorMapper): ErrorConverter {
        return ErrorConverterImpl(setOf(errorMapper))
    }

    @Provides
    fun provideSyncBtcPriceUseCase(
        errorConverter: ErrorConverter,
        @Named(IO_CONTEXT) coroutineContext: CoroutineContext,
        cryptoRepository: CryptoRepository,
    ): SyncBtcPriceUseCase {
        return SyncBtcPriceUseCase(coroutineContext, errorConverter, cryptoRepository)
    }

    @Provides
    fun provideObserveBtcPriceUseCase(
        errorConverter: ErrorConverter,
        @Named(IO_CONTEXT) coroutineContext: CoroutineContext,
        cryptoRepository: CryptoRepository,
    ): ObserveBtcPriceUseCase {
        return ObserveBtcPriceUseCase(coroutineContext, errorConverter, cryptoRepository)
    }

    @Provides
    fun provideSetMinRangeUseCase(
        errorConverter: ErrorConverter,
        @Named(IO_CONTEXT) coroutineContext: CoroutineContext,
        cryptoRepository: CryptoRepository,
    ): SetMinRangeUseCase {
        return SetMinRangeUseCase(coroutineContext, errorConverter, cryptoRepository)
    }

    @Provides
    fun provideSetMaxRangeUseCase(
        errorConverter: ErrorConverter,
        @Named(IO_CONTEXT) coroutineContext: CoroutineContext,
        cryptoRepository: CryptoRepository,
    ): SetMaxRangeUseCase {
        return SetMaxRangeUseCase(coroutineContext, errorConverter, cryptoRepository)
    }


    @Provides
    fun provideCheckReachTargetUseCase(
        errorConverter: ErrorConverter,
        @Named(IO_CONTEXT) coroutineContext: CoroutineContext,
        cryptoRepository: CryptoRepository,
    ): CheckReachTargetsUseCase {
        return CheckReachTargetsUseCase(coroutineContext, errorConverter, cryptoRepository)
    }

    @Provides
    fun provideObserveMaxRateUseCase(
        errorConverter: ErrorConverter,
        @Named(IO_CONTEXT) coroutineContext: CoroutineContext,
        cryptoRepository: CryptoRepository,
    ): ObserveMaxRateUseCase {
        return ObserveMaxRateUseCase(coroutineContext, errorConverter, cryptoRepository)
    }

    @Provides
    fun provideObserveMinRateUseCase(
        errorConverter: ErrorConverter,
        @Named(IO_CONTEXT) coroutineContext: CoroutineContext,
        cryptoRepository: CryptoRepository,
    ): ObserveMinRateUseCase {
        return ObserveMinRateUseCase(coroutineContext, errorConverter, cryptoRepository)
    }


}