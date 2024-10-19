package com.sol.food.domain.model.recipe

data class IngredientX(
    val amount: Double,
    val id: Int,
    val name: String,
    val nutrients: List<Nutrient>,
    val unit: String
)