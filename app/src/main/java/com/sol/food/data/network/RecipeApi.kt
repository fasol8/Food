package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.recipe.NutrientResponse
import com.sol.food.domain.model.recipe.RecipeInformation
import com.sol.food.domain.model.recipe.RecipeRandomResponse
import com.sol.food.domain.model.recipe.RecipeSearchResponse
import com.sol.food.domain.model.recipe.SimilarResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipes/random")
    suspend fun getRandomRecipe(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
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

    @GET("recipes/complexSearch")
    suspend fun getSearchRecipe(
        @Query("query") query: String,
        @Query("number") number: Int = 10,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): RecipeSearchResponse

    @GET("recipes/{id}/information")
    suspend fun getInformationRecipe(
        @Path("id") idRecipe: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): RecipeInformation
}