package com.andro.indieschool.cocktailapp.feature.home.presentation.ui.optional

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

class OptionalAlcoholViewModel(private val repository: HomeRepository): ViewModel() {

    private val _optionalAlcoholState: MutableStateFlow<Resource<List<DrinkModel>>> =
        MutableStateFlow(Resource.Uninitialized)
    val optionalAlcoholState: StateFlow<Resource<List<DrinkModel>>> get() = _optionalAlcoholState.asStateFlow()

    fun getOptionalAlcoholDrinks() {
        viewModelScope.launch(Dispatchers.IO) {
            _optionalAlcoholState.value = Resource.Loading
            repository.getOptionalAlcoholDrink()
                .collect {
                    _optionalAlcoholState.value = it
                }
        }
    }

}