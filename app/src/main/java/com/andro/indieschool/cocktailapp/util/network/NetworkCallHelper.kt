package com.andro.indieschool.cocktailapp.util.network

import retrofit2.Response

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResponse<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResponse.Success(body, null)
            } else {
                NetworkResponse.Error(null, "${response.code()} : ${response.message()}")
            }
        } else {
            NetworkResponse.Error(
                null,
                response.errorBody().toString().ifBlank { response.message() })
        }
    } catch (e: Exception) {
        NetworkResponse.Error(null, e.message.orEmpty().ifBlank { "Network failure" })
    }
}