package com.sol.food.data.repository

import com.sol.food.data.network.MealPlanApi
import com.sol.food.domain.model.mealPlan.MealGenerateResponse
import com.sol.food.domain.model.mealPlan.MealGenerateWeekResponse
import javax.inject.Inject

class MealPlanRepository @Inject constructor(private val api: MealPlanApi) {

    suspend fun getGenerateMealPlan(): MealGenerateResponse {
        return api.getGenerateMealPlan()
    }

    suspend fun getGenerateMealPlanWeek(): MealGenerateWeekResponse {
        return api.getGenerateWeekMealPlan()
    }
}