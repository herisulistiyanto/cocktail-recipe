package com.andro.indieschool.cocktailapp.feature.details.data.remote

import com.andro.indieschool.cocktailapp.feature.details.data.remote.response.DrinkDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkDetailsWebService {

    @GET("v1/1/lookup.php")
    suspend fun getDrinkDetail(@Query("i") idDrink: String): Response<DrinkDetailsResponse>

}