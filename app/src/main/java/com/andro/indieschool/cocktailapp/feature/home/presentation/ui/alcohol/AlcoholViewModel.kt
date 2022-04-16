package com.andro.indieschool.cocktailapp.feature.home.presentation.ui.alcohol

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

class AlcoholViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _alcoholState: MutableStateFlow<Resource<List<DrinkModel>>> =
        MutableStateFlow(Resource.Uninitialized)
    val alcoholState: StateFlow<Resource<List<DrinkModel>>> get() = _alcoholState.asStateFlow()

    fun getAlcoholDrinks() {
        viewModelScope.launch(Dispatchers.IO) {
            _alcoholState.value = Resource.Loading
            repository.getAlcoholDrink()
                .collect {
                    _alcoholState.value = it
                }
        }
    }

}