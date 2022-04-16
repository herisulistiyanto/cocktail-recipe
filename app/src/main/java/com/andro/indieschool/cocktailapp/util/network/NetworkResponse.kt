package com.andro.indieschool.cocktailapp.util.network

sealed class NetworkResponse<out T: Any> {
    data class Success<T : Any>(val data: T, val msg: String?): NetworkResponse<T>()
    data class Error<T: Any>(val data: T?, val msg: String): NetworkResponse<T>()
}
