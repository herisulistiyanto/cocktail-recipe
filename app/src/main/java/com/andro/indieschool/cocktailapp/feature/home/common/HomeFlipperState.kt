package com.andro.indieschool.cocktailapp.feature.home.common

sealed class HomeFlipperState(val state: Int) {
    object Loading : HomeFlipperState(0)
    object Success : HomeFlipperState(1)
    object Error : HomeFlipperState(2)
}