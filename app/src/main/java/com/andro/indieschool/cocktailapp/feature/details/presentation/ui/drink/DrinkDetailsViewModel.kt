package com.andro.indieschool.cocktailapp.feature.details.presentation.ui.drink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andro.indieschool.cocktailapp.feature.details.domain.DrinkDetailsRepository
import com.andro.indieschool.cocktailapp.feature.details.domain.model.DrinkDetailModel
import com.andro.indieschool.cocktailapp.util.network.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DrinkDetailsViewModel(
    private val repository: DrinkDetailsRepository
) : ViewModel() {

    private var _detailState: MutableStateFlow<Resource<DrinkDetailModel>> = MutableStateFlow(
        Resource.Uninitialized
    )
    val detailState: StateFlow<Resource<DrinkDetailModel>> = _detailState.asStateFlow()

    fun getDrinkDetail(idDrink: String) {
        viewModelScope.launch {
            _detailState.value = Resource.Loading
            repository.getDrinkDetails(idDrink)
                .collect {
                    _detailState.value = it
                }
        }
    }

    private var _updateFavState: MutableStateFlow<Resource<Boolean>> = MutableStateFlow(
        Resource.Uninitialized
    )
    val updateFavState: StateFlow<Resource<Boolean>> = _updateFavState.asStateFlow()

    fun updateDrinkFavouriteStatus(idDrink: String, checked: Boolean) {
        viewModelScope.launch {
            _updateFavState.value = Resource.Loading
            repository.updateDrinkFavouriteStatus(idDrink, checked)
                .collect {
                    _updateFavState.value = it
                }
        }
    }

}