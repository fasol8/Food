package com.sol.food.data.repository

import com.sol.food.data.network.IngredientApi
import com.sol.food.domain.model.ingredient.IngredientResponse
import javax.inject.Inject

class IngredientRepository @Inject constructor(private val api: IngredientApi) {

    suspend fun getInformationIngredient(idIngredient: Int): IngredientResponse {
        return api.getInformationIngredient(idIngredient)
    }
}