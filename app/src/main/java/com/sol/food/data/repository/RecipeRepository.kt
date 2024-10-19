package com.sol.food.data.repository

import com.sol.food.data.network.RecipeApi
import com.sol.food.domain.model.recipe.RecipeRandomResponse
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val api: RecipeApi) {

    suspend fun getRandomRecipe(): RecipeRandomResponse {
        return api.getRandomRecipe()
    }
}