package com.sol.food.domain.model.ingredient

data class IngredientSearchResponse(
    val number: Int,
    val offset: Int,
    val results: List<ResultSearch>,
    val totalResults: Int
)