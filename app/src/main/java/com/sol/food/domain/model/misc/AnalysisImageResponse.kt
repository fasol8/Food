package com.sol.food.domain.model.misc

data class AnalysisImageResponse(
    val category: Category,
    val nutrition: Nutrition,
    val recipes: List<Recipe>
)