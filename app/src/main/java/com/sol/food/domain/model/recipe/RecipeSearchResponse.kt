package com.sol.food.domain.model.recipe

data class RecipeSearchResponse(
    val number: Int,
    val offset: Int,
    val results: List<ResultSearch>,
    val totalResults: Int
)