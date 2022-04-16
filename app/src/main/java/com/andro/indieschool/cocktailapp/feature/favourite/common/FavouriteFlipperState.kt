package com.andro.indieschool.cocktailapp.feature.favourite.common

sealed class FavouriteFlipperState(val state: Int) {
    object Loading : FavouriteFlipperState(0)
    object Success : FavouriteFlipperState(1)
    object Error : FavouriteFlipperState(2)
    object Empty : FavouriteFlipperState(3)
}
