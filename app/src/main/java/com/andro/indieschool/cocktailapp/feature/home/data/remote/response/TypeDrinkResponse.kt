package com.andro.indieschool.cocktailapp.feature.home.data.remote.response
import com.google.gson.annotations.SerializedName


data class TypeDrinkResponse(
    @SerializedName("drinks")
    val drinks: List<Drink>?
)

data class Drink(
    @SerializedName("idDrink")
    val idDrink: String?,
    @SerializedName("strDrink")
    val strDrink: String?,
    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String?
)