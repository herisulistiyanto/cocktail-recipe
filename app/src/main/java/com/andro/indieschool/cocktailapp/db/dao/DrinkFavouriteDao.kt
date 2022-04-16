package com.andro.indieschool.cocktailapp.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.andro.indieschool.cocktailapp.db.entity.DrinkEntity

@Dao
interface DrinkFavouriteDao {

    @Query("SELECT * FROM drink_table WHERE favourite = 1")
    suspend fun getAllFavouriteDrink(): List<DrinkEntity>

    @Query("DELETE FROM drink_table WHERE id = :idDrink")
    suspend fun deleteFavouriteDrink(idDrink: String)

    @Transaction
    suspend fun deleteDrink(idDrink: String): List<DrinkEntity> {
        deleteFavouriteDrink(idDrink)
        return getAllFavouriteDrink()
    }

}