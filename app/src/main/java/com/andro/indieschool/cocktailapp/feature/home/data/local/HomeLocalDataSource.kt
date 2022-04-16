package com.andro.indieschool.cocktailapp.feature.home.data.local

import com.andro.indieschool.cocktailapp.db.dao.DrinkDao
import com.andro.indieschool.cocktailapp.db.entity.DrinkEntity
import com.andro.indieschool.cocktailapp.feature.home.data.remote.TypeDrink
import com.andro.indieschool.cocktailapp.feature.home.data.remote.response.Drink

class HomeLocalDataSource(private val drinkDao: DrinkDao) {

    suspend fun insertAllDrinks(drinks: List<Drink>, typeDrink: TypeDrink) {
        drinkDao.insertDrink(
            drinks.map { data ->
                DrinkEntity(
                    idDrink = data.idDrink.orEmpty(),
                    typeDrink = typeDrink.type,
                    nameDrink = data.strDrink.orEmpty(),
                    imageDrink = data.strDrinkThumb.orEmpty()
                )
            }
        )
    }

    suspend fun getAllDrink(typeDrink: TypeDrink): List<DrinkEntity> {
        return drinkDao.getAllDrink(typeDrink.type)
    }

}