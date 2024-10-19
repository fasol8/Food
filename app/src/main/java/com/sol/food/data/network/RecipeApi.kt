package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.recipe.RecipeRandomResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipes/random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("includeNutrition") nutrition: Boolean = true
    ): RecipeRandomResponse
}