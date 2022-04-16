package com.andro.indieschool.cocktailapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_table")
data class DrinkEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val idDrink: String,
    @ColumnInfo(name = "type")
    val typeDrink: String,
    @ColumnInfo(name = "name")
    val nameDrink: String,
    @ColumnInfo(name = "image")
    val imageDrink: String,
    @ColumnInfo(name = "favourite")
    val isFavourite: Boolean = false
)
