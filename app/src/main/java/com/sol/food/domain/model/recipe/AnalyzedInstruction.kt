package com.sol.food.domain.model.recipe

data class AnalyzedInstruction(
    val name: String,
    val steps: List<Step>
)