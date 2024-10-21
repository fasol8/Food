package com.sol.food.data.repository

import com.sol.food.data.network.RecipeApi
import com.sol.food.domain.model.recipe.RecipeDetail
import com.sol.food.domain.model.recipe.RecipeRandomResponse
import com.sol.food.domain.model.recipe.SimilarResponse
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val api: RecipeApi) {

    suspend fun getRandomRecipe(): RecipeRandomResponse {
        return api.getRandomRecipe()
    }

    suspend fun getSimilarRecipe(idRecipe: Int): SimilarResponse {
        return api.getSimilarRecipe(idRecipe)
    }
}