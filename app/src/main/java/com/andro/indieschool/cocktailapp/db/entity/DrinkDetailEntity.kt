package com.andro.indieschool.cocktailapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drink_detail_table")
data class DrinkDetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val idDrink: String,
    @ColumnInfo(name = "alcohol_type")
    val strAlcoholic: String,
    @ColumnInfo(name = "category")
    val strCategory: String,
    @ColumnInfo(name = "name")
    val strDrink: String,
    @ColumnInfo(name = "image")
    val strDrinkThumb: String,
    @ColumnInfo(name = "glass")
    val strGlass: String,
    @ColumnInfo(name = "ingredient")
    val strIngredient: String,
    @ColumnInfo(name = "instructions")
    val strInstructions: String,
    @ColumnInfo(name = "favourite")
    val isFavourite: Boolean = false
)
