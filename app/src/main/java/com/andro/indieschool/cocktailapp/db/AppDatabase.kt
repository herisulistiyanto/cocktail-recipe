package com.andro.indieschool.cocktailapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andro.indieschool.cocktailapp.db.dao.DrinkDao
import com.andro.indieschool.cocktailapp.db.dao.DrinkDetailDao
import com.andro.indieschool.cocktailapp.db.dao.DrinkFavouriteDao
import com.andro.indieschool.cocktailapp.db.entity.DrinkDetailEntity
import com.andro.indieschool.cocktailapp.db.entity.DrinkEntity

@Database(
    entities = [
        DrinkEntity::class,
        DrinkDetailEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun drinkDao(): DrinkDao

    abstract fun drinkDetailDao(): DrinkDetailDao

    abstract fun drinkFavouriteDao(): DrinkFavouriteDao

}