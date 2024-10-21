package com.sol.food.navigation

import com.sol.food.R

sealed class FoodScreen(val route: String, val label: String, val icon: Int = 0) {

    data object HomeScreen : FoodScreen("homeScreen", "Home", R.drawable.ic_home)
    data object MealPlanScreen : FoodScreen("mealPlanScreen", "Meal Plan", R.drawable.ic_meal_plan)
    data object BookmarkScreen : FoodScreen("bookmarkScreen", "Bookmark", R.drawable.ic_bookmarks)
    data object MenuScreen : FoodScreen("foodScreen", "Menu", R.drawable.ic_menu)

    data object RecipeScreen :
        FoodScreen("recipeScreen", "Recipe Screen")

}