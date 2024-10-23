package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.ingredient.IngredientResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IngredientApi {

    @GET("food/ingredients/{id}/information")
    suspend fun getInformationIngredient(
        @Path("id") idRecipe: Int,
        @Query("amount") amount: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): IngredientResponse
}