package com.andro.indieschool.cocktailapp.feature.details.data.mapper

import com.andro.indieschool.cocktailapp.util.extension.orDefaultField
import com.andro.indieschool.cocktailapp.feature.details.data.remote.response.DrinkDetail
import com.andro.indieschool.cocktailapp.feature.details.domain.model.DrinkDetailModel
import com.andro.indieschool.cocktailapp.db.entity.DrinkDetailEntity

internal fun DrinkDetail.toEntity(): DrinkDetailEntity {
    return DrinkDetailEntity(
        idDrink = idDrink.orDefaultField(),
        strAlcoholic = strAlcoholic.orDefaultField(),
        strCategory = strCategory.orDefaultField(),
        strDrink = strDrink.orDefaultField(),
        strDrinkThumb = strDrinkThumb.orDefaultField(),
        strGlass = strGlass.orDefaultField(),
        strIngredient = assocIngredient(),
        strInstructions = strInstructions.orDefaultField()
    )
}

internal fun DrinkDetailEntity.toModel(): DrinkDetailModel {
    return DrinkDetailModel(
        idDrink,
        strAlcoholic,
        strCategory,
        strDrink,
        strDrinkThumb,
        strGlass,
        strIngredient,
        strInstructions
    )
}

private fun DrinkDetail.joinIngredient(): String {
    return DrinkDetail::class.members.filter {
        it.name.startsWith("strIngredient", true) &&
                it.call(this) != null
    }.joinToString("\n") {
        "\u2022 " + it.call(this).toString()
    }
}

private fun DrinkDetail.assocIngredient(): String {
    val mapIngredient = extractValueFromParam("strIngredient")
    val mapMeasure = extractValueFromParam("strMeasure")
    return (mapIngredient.keys + mapMeasure.keys).associate {
        mapIngredient[it].orDefaultField() to mapMeasure[it].orEmpty()
    }.map {
        "\u2022 ${it.key} : ${it.value}"
    }.joinToString("\n")
}

private fun DrinkDetail.extractValueFromParam(paramKeyword: String): Map<String, String> {
    return DrinkDetail::class.members.filter {
        it.name.startsWith(paramKeyword, true) &&
                it.call(this) != null
    }.associate {
        it.name.substringAfter(paramKeyword) to it.call(this).toString()
    }
}