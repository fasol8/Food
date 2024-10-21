package com.sol.food.domain.model.recipe

data class Good(
    override val amount: String,
    override val indented: Boolean,
    override val percentOfDailyNeeds: Double,
    override val title: String
) : NutrientGorB