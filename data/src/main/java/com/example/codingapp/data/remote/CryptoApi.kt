package com.example.codingapp.data.remote

import com.example.codingapp.data.remote.models.CryptoPriceRemoteDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET("v1/bpi/currentprice.json")
    suspend fun getBtcCurrentPrice(): CryptoPriceRemoteDto

}