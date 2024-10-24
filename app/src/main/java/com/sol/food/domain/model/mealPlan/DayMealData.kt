package com.sol.food.domain.model.mealPlan

data class DayMealData(
    val nutrients: Nutrients,
    val meals: List<Meal>
)
