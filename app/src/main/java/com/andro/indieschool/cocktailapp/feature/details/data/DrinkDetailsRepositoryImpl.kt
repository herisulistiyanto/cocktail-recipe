package com.andro.indieschool.cocktailapp.feature.details.data

import com.andro.indieschool.cocktailapp.feature.details.data.local.DrinkDetailsLocalDataSource
import com.andro.indieschool.cocktailapp.feature.details.data.remote.DrinkDetailsRemoteDataSource
import com.andro.indieschool.cocktailapp.feature.details.data.remote.response.DrinkDetail
import com.andro.indieschool.cocktailapp.feature.details.data.remote.response.DrinkDetailsResponse
import com.andro.indieschool.cocktailapp.feature.details.domain.DrinkDetailsRepository
import com.andro.indieschool.cocktailapp.feature.details.domain.model.DrinkDetailModel
import com.andro.indieschool.cocktailapp.util.network.NetworkResponse
import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.util.network.singleFetchNetworkBoundFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DrinkDetailsRepositoryImpl(
    private val remoteDataSource: DrinkDetailsRemoteDataSource,
    private val localDataSource: DrinkDetailsLocalDataSource
) : DrinkDetailsRepository {

    private suspend fun getDetailFromDb(idDrink: String): DrinkDetailModel? {
        return localDataSource.getDrinkDetail(idDrink)
    }

    private suspend fun getDetailFromApi(idDrink: String): NetworkResponse<DrinkDetailsResponse> {
        return remoteDataSource.getDrinkDetails(idDrink)
    }

    private suspend fun insertDetailDataToDb(model: DrinkDetail) {
        localDataSource.insertDrinkDetail(model)
    }

    override fun getDrinkDetails(idDrink: String): Flow<Resource<DrinkDetailModel>> {
        return singleFetchNetworkBoundFlow(
            dbFetcher = { getDetailFromDb(idDrink) },
            apiFetcher = { getDetailFromApi(idDrink) },
            cacheValidator = { cachedData -> cachedData != null },
            dataPersister = { model ->
                insertDetailDataToDb(model.drinkDetails.orEmpty().first())
            }
        ).catch { Resource.Error(null, it.message) }
    }

    override fun updateDrinkFavouriteStatus(
        idDrink: String,
        checked: Boolean
    ): Flow<Resource<Boolean>> {
        return flow<Resource<Boolean>> {
            localDataSource.updateDrinkFavouriteStatus(idDrink, checked).also {
                emit(Resource.Success(true))
            }
        }.catch {
            emit(Resource.Error(false, it.message))
        }
    }
}