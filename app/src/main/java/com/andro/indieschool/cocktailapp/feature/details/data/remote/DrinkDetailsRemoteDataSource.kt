package com.andro.indieschool.cocktailapp.feature.details.data.remote

import com.andro.indieschool.cocktailapp.util.network.NetworkResponse
import com.andro.indieschool.cocktailapp.util.network.safeApiCall
import com.andro.indieschool.cocktailapp.feature.details.data.remote.response.DrinkDetailsResponse

class DrinkDetailsRemoteDataSource(private val service: DrinkDetailsWebService) {

    suspend fun getDrinkDetails(idDrink: String): NetworkResponse<DrinkDetailsResponse> {
        return safeApiCall { service.getDrinkDetail(idDrink) }
    }

}