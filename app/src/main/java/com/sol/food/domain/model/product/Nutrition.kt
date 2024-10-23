package com.sol.food.domain.model.product

import com.sol.food.domain.model.ingredient.Nutrient

data class Nutrition(
    val caloricBreakdown: CaloricBreakdown,
    val nutrients: List<Nutrient>
)