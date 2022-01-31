package com.example.codingapp.domain.exception

fun interface ErrorConverter {
    fun convert(t: Throwable): Throwable
}