package com.sol.food.domain.model.recipe

data class Nutrition(
    val caloricBreakdown: CaloricBreakdown,
    val flavonoids: List<Flavonoid>,
    val ingredients: List<Ingredient>,
    val nutrients: List<Nutrient>,
    val properties: List<Property>,
    val weightPerServing: WeightPerServing
)