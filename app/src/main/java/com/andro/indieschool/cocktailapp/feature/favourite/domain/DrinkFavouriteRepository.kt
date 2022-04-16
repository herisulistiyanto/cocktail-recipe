package com.andro.indieschool.cocktailapp.feature.favourite.domain

import com.andro.indieschool.cocktailapp.feature.favourite.domain.model.DrinkFavouriteModel
import com.andro.indieschool.cocktailapp.util.network.Resource
import kotlinx.coroutines.flow.Flow

interface DrinkFavouriteRepository {
    fun getAllFavouriteDrink(): Flow<Resource<List<DrinkFavouriteModel>>>
    fun deleteFavouriteDrink(idDrink: String): Flow<Resource<List<DrinkFavouriteModel>>>
}