package com.example.codingapp.domain.exception

fun interface ErrorMapper {
    fun mapError(e: Throwable): Throwable
}