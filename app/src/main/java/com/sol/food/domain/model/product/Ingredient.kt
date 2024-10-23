package com.sol.food.domain.model.product

import com.google.gson.annotations.SerializedName

data class Ingredient(
    val description: String,
    val name: String,
    @SerializedName("safety_level") val safetyLevel: String
)