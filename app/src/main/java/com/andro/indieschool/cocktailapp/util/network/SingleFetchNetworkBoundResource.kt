package com.andro.indieschool.cocktailapp.util.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class SingleFetchNetworkBoundResource<T : Any, U : Any> : NetworkBoundResource<T, U>() {

    private var hasDataBeenFetched: Boolean = false

    override fun toFlow(): Flow<Resource<T>> {
        return flow {
            val cachedData = getFromDb(isRefreshed = hasDataBeenFetched)
            if (validateCache(cachedData)) {
                emit(Resource.Success(cachedData!!, isCached = true))
            } else {
                emit(Resource.Loading)
            }

            if (!hasDataBeenFetched) {
                when (val apiResponse = getFromApi()) {
                    is NetworkResponse.Success -> {
                        persistData(apiResponse.data)
                        hasDataBeenFetched = true
                        val refreshedData = getFromDb(isRefreshed = true)
                        emit(Resource.Success(refreshedData!!, isCached = false))
                    }
                    is NetworkResponse.Error -> {
                        val errorMsg = apiResponse.msg
                        emit(Resource.Error(cachedData, errorMsg))
                    }
                }
            } else {
                cachedData?.let {
                    emit(Resource.Success(it, isCached = false))
                } ?: Resource.Error(cachedData, null)
            }
        }
    }
}

inline fun <T : Any, U : Any> singleFetchNetworkBoundResource(
    crossinline dbFetcher: suspend () -> T?,
    crossinline apiFetcher: suspend () -> NetworkResponse<U>,
    crossinline cacheValidator: suspend (T?) -> Boolean,
    crossinline dataPersister: suspend (U) -> Unit
): SingleFetchNetworkBoundResource<T, U> {
    return object : SingleFetchNetworkBoundResource<T, U>() {
        override suspend fun getFromDb(isRefreshed: Boolean): T? = dbFetcher()
        override suspend fun getFromApi(): NetworkResponse<U> = apiFetcher()
        override suspend fun validateCache(cachedData: T?): Boolean = cacheValidator(cachedData)
        override suspend fun persistData(apiData: U) = dataPersister(apiData)
    }
}

inline fun <T : Any, U : Any> singleFetchNetworkBoundFlow(
    crossinline dbFetcher: suspend () -> T?,
    crossinline apiFetcher: suspend () -> NetworkResponse<U>,
    crossinline cacheValidator: suspend (T?) -> Boolean,
    crossinline dataPersister: suspend (U) -> Unit
): Flow<Resource<T>> {
    val resource =
        singleFetchNetworkBoundResource(dbFetcher, apiFetcher, cacheValidator, dataPersister)
    return resource.toFlow()
}

inline fun <T : Any, U : Any> singleFetchNetworkBoundResourceLazy(
    crossinline dbFetcher: suspend () -> T?,
    crossinline apiFetcher: suspend () -> NetworkResponse<U>,
    crossinline cacheValidator: suspend (T?) -> Boolean,
    crossinline dataPersister: suspend (U) -> Unit
): Lazy<SingleFetchNetworkBoundResource<T, U>> {
    return lazyOf(
        singleFetchNetworkBoundResource(
            dbFetcher,
            apiFetcher,
            cacheValidator,
            dataPersister
        )
    )
}