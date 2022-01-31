package com.example.codingapp.data.remote.models

data class CryptoPriceRemoteDto(
    val chartName: String,
    val disclaimer: String,
    val time: TimeRemoteDto,
    val bpi: BpiRemoteDto,
)