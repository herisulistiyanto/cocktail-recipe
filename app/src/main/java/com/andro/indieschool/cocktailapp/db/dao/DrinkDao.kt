package com.andro.indieschool.cocktailapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andro.indieschool.cocktailapp.db.entity.DrinkEntity

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = DrinkEntity::class)
    suspend fun insertDrink(drinks: List<DrinkEntity>)

    @Query("SELECT * FROM drink_table WHERE type = :type ORDER BY id ASC")
    suspend fun getAllDrink(type: String): List<DrinkEntity>

    @Query("SELECT * FROM drink_table WHERE id = :idDrink")
    suspend fun getDrink(idDrink: String): DrinkEntity
}