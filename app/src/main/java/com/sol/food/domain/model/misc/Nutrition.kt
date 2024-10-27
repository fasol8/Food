package com.sol.food.domain.model.misc

data class Nutrition(
    val calories: Calories,
    val carbs: Carbs,
    val fat: Fat,
    val protein: Protein,
    val recipesUsed: Int
)