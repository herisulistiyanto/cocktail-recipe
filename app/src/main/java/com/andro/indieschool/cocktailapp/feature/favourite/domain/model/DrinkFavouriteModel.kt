package com.andro.indieschool.cocktailapp.feature.favourite.domain.model

data class DrinkFavouriteModel(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String,
    val isFavourite: Boolean = false
)
