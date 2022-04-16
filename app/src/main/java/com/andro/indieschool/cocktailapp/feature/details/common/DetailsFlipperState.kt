package com.andro.indieschool.cocktailapp.feature.details.common

sealed class DetailsFlipperState(val state: Int) {
    object Loading : DetailsFlipperState(0)
    object Success : DetailsFlipperState(1)
    object Error : DetailsFlipperState(2)
}
