package com.andro.indieschool.cocktailapp.feature.favourite.data.local

import com.andro.indieschool.cocktailapp.db.dao.DrinkFavouriteDao
import com.andro.indieschool.cocktailapp.db.entity.DrinkEntity

class DrinkFavouriteLocalDataSource(private val dao: DrinkFavouriteDao) {

    suspend fun getAllFavouriteDrinks(): List<DrinkEntity> {
        return dao.getAllFavouriteDrink()
    }

    suspend fun deleteFavouriteDrink(idDrink: String): List<DrinkEntity> {
        return dao.deleteDrink(idDrink)
    }

}