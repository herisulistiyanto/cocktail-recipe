package com.andro.indieschool.cocktailapp.feature.home.data.remote

import com.andro.indieschool.cocktailapp.feature.home.data.remote.response.TypeDrinkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface HomeNetworkService {

    @GET("v1/1/filter.php")
    suspend fun getDrink(@Query("a") type: String): Response<TypeDrinkResponse>

}

sealed class TypeDrink(val type: String) {
    object NonAlcoholic : TypeDrink("Non_Alcoholic")
    object Alcoholic : TypeDrink("Alcoholic")
    object OptionalAlcoholic : TypeDrink("Optional_Alcohol")
}