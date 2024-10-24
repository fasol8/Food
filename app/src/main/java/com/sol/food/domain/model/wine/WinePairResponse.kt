package com.sol.food.domain.model.wine

data class WinePairResponse(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<RecommendedWine>
)