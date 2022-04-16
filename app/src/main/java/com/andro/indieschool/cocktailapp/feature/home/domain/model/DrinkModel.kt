package com.andro.indieschool.cocktailapp.feature.home.domain.model

data class DrinkModel(
    val idDrink: String?,
    val strDrink: String?,
    val strDrinkThumb: String?,
    val isFavourite: Boolean = false
)