package com.sol.food.domain.model.misc

data class ClassifyImageResponse(
    val category: String,
    val probability: Double,
    val status: String
)