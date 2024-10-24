package com.sol.food.domain.model.wine

data class WineResponse(
    val recommendedWines: List<RecommendedWine>,
    val totalFound: Int
)