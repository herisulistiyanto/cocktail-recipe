package com.andro.indieschool.cocktailapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andro.indieschool.cocktailapp.db.entity.DrinkDetailEntity

@Dao
interface DrinkDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = DrinkDetailEntity::class)
    suspend fun insertDrinkDetail(drinkDetail: DrinkDetailEntity)

    @Query("SELECT * FROM drink_detail_table WHERE id = :idDrink")
    suspend fun getDrinkDetail(idDrink: String): DrinkDetailEntity?

    @Query("UPDATE drink_table SET favourite = :checked WHERE id = :idDrink")
    suspend fun updateDrinkFavouriteStatus(idDrink: String, checked: Boolean)

    @Query("UPDATE drink_detail_table SET favourite = :checked WHERE id = :idDrink")
    suspend fun updateDrinkDetailsFavouriteStatus(idDrink: String, checked: Boolean)

    @Transaction
    suspend fun updateFavouriteStatus(idDrink: String, checked: Boolean) {
        updateDrinkFavouriteStatus(idDrink, checked)
        updateDrinkDetailsFavouriteStatus(idDrink, checked)
    }

}