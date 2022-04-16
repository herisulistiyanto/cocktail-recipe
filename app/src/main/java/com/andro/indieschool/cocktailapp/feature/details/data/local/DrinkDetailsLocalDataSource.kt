package com.andro.indieschool.cocktailapp.feature.details.data.local

import com.andro.indieschool.cocktailapp.db.dao.DrinkDetailDao
import com.andro.indieschool.cocktailapp.feature.details.data.mapper.toEntity
import com.andro.indieschool.cocktailapp.feature.details.data.mapper.toModel
import com.andro.indieschool.cocktailapp.feature.details.data.remote.response.DrinkDetail
import com.andro.indieschool.cocktailapp.feature.details.domain.model.DrinkDetailModel

class DrinkDetailsLocalDataSource(private val dao: DrinkDetailDao) {

    suspend fun insertDrinkDetail(drinkDetail: DrinkDetail) {
        dao.insertDrinkDetail(drinkDetail.toEntity())
    }

    suspend fun getDrinkDetail(idDrink: String): DrinkDetailModel? {
        return dao.getDrinkDetail(idDrink)?.toModel()
    }

    suspend fun updateDrinkFavouriteStatus(idDrink: String, checked: Boolean) {
        dao.updateFavouriteStatus(idDrink, checked)
    }

}