package com.andro.indieschool.cocktailapp.feature.favourite.data

import com.andro.indieschool.cocktailapp.feature.favourite.data.local.DrinkFavouriteLocalDataSource
import com.andro.indieschool.cocktailapp.feature.favourite.domain.DrinkFavouriteRepository
import com.andro.indieschool.cocktailapp.feature.favourite.domain.model.DrinkFavouriteModel
import com.andro.indieschool.cocktailapp.util.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DrinkFavouriteRepositoryImpl(
    private val localDataSource: DrinkFavouriteLocalDataSource
) : DrinkFavouriteRepository {

    override fun getAllFavouriteDrink(): Flow<Resource<List<DrinkFavouriteModel>>> {
        return flow {
            emit(Resource.Loading)
            val data = localDataSource.getAllFavouriteDrinks().map {
                DrinkFavouriteModel(
                    idDrink = it.idDrink,
                    strDrink = it.nameDrink,
                    strDrinkThumb = it.imageDrink,
                    isFavourite = it.isFavourite
                )
            }
            emit(Resource.Success(data))
        }.catch {
            emit(Resource.Error(null, it.message))
        }
    }

    override fun deleteFavouriteDrink(idDrink: String): Flow<Resource<List<DrinkFavouriteModel>>> {
        return flow {
            emit(Resource.Loading)
            val data = localDataSource.deleteFavouriteDrink(idDrink).map {
                DrinkFavouriteModel(
                    idDrink = it.idDrink,
                    strDrink = it.nameDrink,
                    strDrinkThumb = it.imageDrink,
                    isFavourite = it.isFavourite
                )
            }
            emit(Resource.Success(data))
        }.catch {
            emit(Resource.Error(null, it.message))
        }
    }

}