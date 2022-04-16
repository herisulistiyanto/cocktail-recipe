package com.andro.indieschool.cocktailapp.feature.home.presentation.ui.nonalcohol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andro.indieschool.cocktailapp.util.network.Resource
import com.andro.indieschool.cocktailapp.feature.home.domain.repository.HomeRepository
import com.andro.indieschool.cocktailapp.feature.home.domain.model.DrinkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NonAlcoholViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _nonAlcoholState: MutableStateFlow<Resource<List<DrinkModel>>> =
        MutableStateFlow(Resource.Uninitialized)
    val nonAlcoholState: StateFlow<Resource<List<DrinkModel>>> get() = _nonAlcoholState.asStateFlow()

    fun getNonAlcoholDrinks() {
        viewModelScope.launch(Dispatchers.IO) {
            _nonAlcoholState.value = Resource.Loading
            repository.getNonAlcoholDrink()
                .collect {
                    _nonAlcoholState.value = it
                }
        }
    }
}