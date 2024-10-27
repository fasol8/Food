package com.sol.food.domain.model.misc

interface NutritionInfo {
    val confidenceRange95Percent: ConfidenceRange95Percent
    val standardDeviation: Double
    val unit: String
    val value: Int
}