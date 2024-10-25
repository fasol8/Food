package com.sol.food.data.repository

import com.sol.food.data.network.RecipeApi
import com.sol.food.domain.model.recipe.NutrientResponse
import com.sol.food.domain.model.recipe.RecipeInformation
import com.sol.food.domain.model.recipe.RecipeRandomResponse
import com.sol.food.domain.model.recipe.RecipeSearchResponse
import com.sol.food.domain.model.recipe.SimilarResponse
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val api: RecipeApi) {

    suspend fun getRandomRecipe(): RecipeRandomResponse {
        return api.getRandomRecipe()
    }

    suspend fun getSimilarRecipe(idRecipe: Int): SimilarResponse {
        return api.getSimilarRecipe(idRecipe)
    }

    suspend fun getNutrientRecipe(idRecipe: Int): NutrientResponse {
        return api.getNutrientRecipe(idRecipe)
    }

    suspend fun getSearchRecipe(query: String): RecipeSearchResponse {
        return api.getSearchRecipe(query)
    }

    suspend fun getInformationRecipe(idRecipe: Int): RecipeInformation {
        return api.getInformationRecipe(idRecipe)
    }
}