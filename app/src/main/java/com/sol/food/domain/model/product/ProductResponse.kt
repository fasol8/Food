package com.sol.food.domain.model.product

data class ProductResponse(
    val aisle: String,
    val badges: List<String>,
    val breadcrumbs: List<String>,
    val generatedText: String,
    val id: Int,
    val imageType: String,
    val importantBadges: List<String>,
    val ingredientCount: Int,
    val ingredientList: String,
    val ingredients: List<Ingredient>,
    val likes: Int,
    val nutrition: Nutrition,
    val price: Double,
    val servings: Servings,
    val spoonacularScore: Double,
    val title: String
)