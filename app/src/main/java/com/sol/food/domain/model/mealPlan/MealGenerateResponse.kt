package com.sol.food.domain.model.mealPlan

data class MealGenerateResponse(
    val meals: List<Meal>,
    val nutrients: Nutrients
)