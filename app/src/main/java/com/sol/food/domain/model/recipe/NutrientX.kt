package com.sol.food.domain.model.recipe

data class NutrientX(
    val amount: Double,
    val name: String,
    val percentOfDailyNeeds: Double,
    val unit: String
)