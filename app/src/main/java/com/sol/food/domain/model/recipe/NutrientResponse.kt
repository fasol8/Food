package com.sol.food.domain.model.recipe

data class NutrientResponse(
    val bad: List<Bad>,
    val caloricBreakdown: CaloricBreakdown,
    val calories: String,
    val carbs: String,
    val expires: Long,
    val fat: String,
    val flavonoids: List<Flavonoid>,
    val good: List<Good>,
    val ingredients: List<Ingredient>,
    val isStale: Boolean,
    val nutrients: List<Nutrient>,
    val properties: List<Property>,
    val protein: String,
    val weightPerServing: WeightPerServing
)