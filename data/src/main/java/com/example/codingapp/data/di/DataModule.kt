package com.example.codingapp.data.di

import android.app.Application
import com.example.codingapp.data.BuildConfig
import com.example.codingapp.data.errors.RemoteErrorMapper
import com.example.codingapp.data.local.CryptoPreference
import com.example.codingapp.data.local.CryptoPreferenceImpl
import com.example.codingapp.data.remote.CryptoApi
import com.example.codingapp.data.repository.CryptoRepositoryImpl
import com.example.codingapp.domain.di.ERROR_MAPPER_NETWORK
import com.example.codingapp.domain.di.IO_CONTEXT
import com.example.codingapp.domain.exception.ErrorMapper
import com.example.codingapp.domain.repository.CryptoRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideCryptoRepository(
        cryptoApi: CryptoApi,
        cryptoPreference: CryptoPreference,
    ): CryptoRepository {
        return CryptoRepositoryImpl(cryptoApi, cryptoPreference)
    }

    //   Network

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )

        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(false)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        val baseUrl = "https://api.coindesk.com"
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    fun provideCryptoApi(retrofit: Retrofit): CryptoApi =
        retrofit.create(CryptoApi::class.java)

    //   Local


    @Singleton
    @Provides
    fun provideCryptoPreference(application: Application): CryptoPreference {
        return CryptoPreferenceImpl(application)
    }

    //  General

    @Provides
    @Named(IO_CONTEXT)
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    @Named(ERROR_MAPPER_NETWORK)
    fun provideErrorMapper(moshi: Moshi): ErrorMapper {
        return RemoteErrorMapper(moshi)
    }

}