package com.andro.indieschool.cocktailapp.feature.details.domain

import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.feature.details.domain.model.DrinkDetailModel
import kotlinx.coroutines.flow.Flow

interface DrinkDetailsRepository {

    fun getDrinkDetails(idDrink: String): Flow<Resource<DrinkDetailModel>>
    fun updateDrinkFavouriteStatus(idDrink: String, checked: Boolean): Flow<Resource<Boolean>>

}