package com.andro.indieschool.cocktailapp.feature.home.domain.repository

import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.feature.home.domain.model.DrinkModel
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getNonAlcoholDrink(): Flow<Resource<List<DrinkModel>>>

    fun getAlcoholDrink(): Flow<Resource<List<DrinkModel>>>

    fun getOptionalAlcoholDrink(): Flow<Resource<List<DrinkModel>>>

}