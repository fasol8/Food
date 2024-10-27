package com.sol.food.domain.model.misc

data class Carbs(
    override val confidenceRange95Percent: ConfidenceRange95Percent,
    override val standardDeviation: Double,
    override val unit: String,
    override val value: Int
) : NutritionInfo