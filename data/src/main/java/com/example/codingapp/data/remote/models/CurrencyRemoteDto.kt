package com.example.codingapp.data.remote.models

data class CurrencyRemoteDto(
    val code: String,
    val symbol: String,
    val rate: String,
    val description: String,
    val rate_float: Float
)