package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.recipe.NutrientResponse
import com.sol.food.domain.model.recipe.RecipeDetail
import com.sol.food.domain.model.recipe.RecipeRandomResponse
import com.sol.food.domain.model.recipe.SimilarResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipes/random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("includeNutrition") nutrition: Boolean = true
    ): RecipeRandomResponse

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipe(
        @Path("id") idRecipe: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): SimilarResponse


    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getNutrientRecipe(
        @Path("id") idRecipe: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): NutrientResponse
}