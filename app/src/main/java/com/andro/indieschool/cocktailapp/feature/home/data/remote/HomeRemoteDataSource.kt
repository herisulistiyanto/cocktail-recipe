package com.andro.indieschool.cocktailapp.feature.home.data.remote

import com.andro.indieschool.cocktailapp.util.network.NetworkResponse
import com.andro.indieschool.cocktailapp.util.network.safeApiCall
import com.andro.indieschool.cocktailapp.feature.home.data.remote.response.TypeDrinkResponse

class HomeRemoteDataSource(
    private val homeNetworkService: HomeNetworkService
) {

    suspend fun getDrink(typeDrink: TypeDrink): NetworkResponse<TypeDrinkResponse> {
        return safeApiCall { homeNetworkService.getDrink(typeDrink.type) }
    }
}