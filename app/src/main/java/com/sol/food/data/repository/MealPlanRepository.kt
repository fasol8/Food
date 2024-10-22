package com.sol.food.data.repository

import com.sol.food.data.network.MealPlanApi
import com.sol.food.domain.model.mealPlan.MealGenerateResponse
import javax.inject.Inject

class MealPlanRepository @Inject constructor(private val api: MealPlanApi) {

    suspend fun getGenerateMealPlan(time: String): MealGenerateResponse {
        return api.getGenerateMealPlan(time)
    }
}