package com.sol.food.domain.model.recipe

data class SimilarResponseItem(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)