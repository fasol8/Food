package com.sol.food.data.network

import com.sol.food.BuildConfig
import com.sol.food.domain.model.mealPlan.MealGenerateResponse
import com.sol.food.domain.model.mealPlan.MealGenerateWeekResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealPlanApi {

    @GET("mealplanner/generate")
    suspend fun getGenerateMealPlan(
        @Query("timeFrame") timeFrame: String = "day",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): MealGenerateResponse

    @GET("mealplanner/generate")
    suspend fun getGenerateWeekMealPlan(
        @Query("timeFrame") timeFrame: String = "week",
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): MealGenerateWeekResponse
}