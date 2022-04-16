package com.andro.indieschool.cocktailapp.util.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<T : Any, U : Any> {

    abstract suspend fun getFromDb(isRefreshed: Boolean): T?
    abstract suspend fun validateCache(cachedData: T?): Boolean
    abstract suspend fun getFromApi(): NetworkResponse<U>
    abstract suspend fun persistData(apiData: U)

    open fun toFlow(): Flow<Resource<T>> {
        return flow {
            val cachedData = getFromDb(isRefreshed = false)
            if (validateCache(cachedData)) {
                emit(Resource.Success(cachedData!!, isCached = true))
            } else {
                emit(Resource.Loading)
            }

            when (val apiResponse = getFromApi()) {
                is NetworkResponse.Success -> {
                    persistData(apiData = apiResponse.data)
                    val refreshedData = getFromDb(isRefreshed = false)
                    emit(Resource.Success(refreshedData!!, isCached = false))
                }
                is NetworkResponse.Error -> {
                    val errorMsg = apiResponse.msg
                    emit(Resource.Error(cachedData, errorMsg))
                }
            }
        }
    }
}

inline fun <T : Any, U : Any> networkBoundResource(
    crossinline dbFetcher: suspend () -> T?,
    crossinline apiFetcher: suspend () -> NetworkResponse<U>,
    crossinline cacheValidator: suspend (T?) -> Boolean,
    crossinline dataPersister: suspend (U) -> Unit
): NetworkBoundResource<T, U> {
    return object : NetworkBoundResource<T, U>() {
        override suspend fun getFromDb(isRefreshed: Boolean): T? = dbFetcher()
        override suspend fun getFromApi(): NetworkResponse<U> = apiFetcher()
        override suspend fun validateCache(cachedData: T?): Boolean = cacheValidator(cachedData)
        override suspend fun persistData(apiData: U) = dataPersister(apiData)
    }
}

inline fun <T : Any, U : Any> networkBoundFlow(
    crossinline dbFetcher: suspend () -> T?,
    crossinline apiFetcher: suspend () -> NetworkResponse<U>,
    crossinline cacheValidator: suspend (T?) -> Boolean,
    crossinline dataPersister: suspend (U) -> Unit
): Flow<Resource<T>> {
    val resource = networkBoundResource(dbFetcher, apiFetcher, cacheValidator, dataPersister)
    return resource.toFlow()
}

inline fun <T : Any, U : Any> networkBoundResourceLazy(
    crossinline dbFetcher: suspend () -> T?,
    crossinline apiFetcher: suspend () -> NetworkResponse<U>,
    crossinline cacheValidator: suspend (T?) -> Boolean,
    crossinline dataPersister: suspend (U) -> Unit
): Lazy<NetworkBoundResource<T, U>> {
    return lazyOf(networkBoundResource(dbFetcher, apiFetcher, cacheValidator, dataPersister))
}