package com.andro.indieschool.cocktailapp.feature.favourite.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andro.indieschool.cocktailapp.feature.favourite.domain.DrinkFavouriteRepository
import com.andro.indieschool.cocktailapp.feature.favourite.domain.model.DrinkFavouriteModel
import com.andro.indieschool.cocktailapp.util.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val repository: DrinkFavouriteRepository
) : ViewModel() {

    private val _favouriteState: MutableStateFlow<Resource<List<DrinkFavouriteModel>>> =
        MutableStateFlow(Resource.Uninitialized)
    val favouriteState = _favouriteState.asStateFlow()

    fun getAllFavourite() {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteState.value = Resource.Loading
            repository.getAllFavouriteDrink()
                .collect {
                    _favouriteState.value = it
                }
        }
    }

    fun deleteFavourite(idDrink: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _favouriteState.value = Resource.Loading
            repository.deleteFavouriteDrink(idDrink)
                .collect {
                    _favouriteState.value = it
                }
        }
    }

}