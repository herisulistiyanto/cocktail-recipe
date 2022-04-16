package com.andro.indieschool.cocktailapp.feature.home.data.repository

import com.andro.indieschool.cocktailapp.feature.home.data.local.HomeLocalDataSource
import com.andro.indieschool.cocktailapp.feature.home.data.remote.HomeRemoteDataSource
import com.andro.indieschool.cocktailapp.feature.home.data.remote.TypeDrink
import com.andro.indieschool.cocktailapp.feature.home.data.remote.response.Drink
import com.andro.indieschool.cocktailapp.feature.home.data.remote.response.TypeDrinkResponse
import com.andro.indieschool.cocktailapp.feature.home.domain.model.DrinkModel
import com.andro.indieschool.cocktailapp.feature.home.domain.repository.HomeRepository
import com.andro.indieschool.cocktailapp.util.network.NetworkResponse
import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.util.network.singleFetchNetworkBoundFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class HomeRepositoryImpl(
    private val remoteDataSource: HomeRemoteDataSource,
    private val localDataSource: HomeLocalDataSource
) : HomeRepository {

    private suspend fun getDrinkFromDb(typeDrink: TypeDrink): List<DrinkModel> {
        return localDataSource.getAllDrink(typeDrink).map { model ->
            DrinkModel(model.idDrink, model.nameDrink, model.imageDrink, model.isFavourite)
        }
    }

    private suspend fun saveDrinkIntoDb(drinks: List<Drink>, typeDrink: TypeDrink) {
        localDataSource.insertAllDrinks(drinks, typeDrink)
    }

    private suspend fun getDrinkFromApi(typeDrink: TypeDrink): NetworkResponse<TypeDrinkResponse> {
        return remoteDataSource.getDrink(typeDrink)
    }

    override fun getNonAlcoholDrink(): Flow<Resource<List<DrinkModel>>> {
        return singleFetchNetworkBoundFlow(
            dbFetcher = { getDrinkFromDb(TypeDrink.NonAlcoholic) },
            apiFetcher = { getDrinkFromApi(TypeDrink.NonAlcoholic) },
            cacheValidator = { cachedData -> cachedData.isNullOrEmpty().not() },
            dataPersister = { data ->
                saveDrinkIntoDb(
                    data.drinks.orEmpty(),
                    TypeDrink.NonAlcoholic
                )
            }
        ).flowOn(Dispatchers.IO)
    }

    override fun getAlcoholDrink(): Flow<Resource<List<DrinkModel>>> {
        return singleFetchNetworkBoundFlow(
            dbFetcher = { getDrinkFromDb(TypeDrink.Alcoholic) },
            apiFetcher = { getDrinkFromApi(TypeDrink.Alcoholic) },
            cacheValidator = { cachedData -> cachedData.isNullOrEmpty().not() },
            dataPersister = { data -> saveDrinkIntoDb(data.drinks.orEmpty(), TypeDrink.Alcoholic) }
        ).flowOn(Dispatchers.IO)
    }

    override fun getOptionalAlcoholDrink(): Flow<Resource<List<DrinkModel>>> {
        return singleFetchNetworkBoundFlow(
            dbFetcher = { getDrinkFromDb(TypeDrink.OptionalAlcoholic) },
            apiFetcher = { getDrinkFromApi(TypeDrink.OptionalAlcoholic) },
            cacheValidator = { cachedData -> cachedData.isNullOrEmpty().not() },
            dataPersister = { data ->
                saveDrinkIntoDb(
                    data.drinks.orEmpty(),
                    TypeDrink.OptionalAlcoholic
                )
            }
        ).flowOn(Dispatchers.IO)
    }
}