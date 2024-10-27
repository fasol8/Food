package com.sol.food.navigation

import com.sol.food.R

sealed class FoodScreen(val route: String, val label: String, val icon: Int = 0) {

    data object HomeScreen : FoodScreen("homeScreen", "Home", R.drawable.ic_home)
    data object MealPlanScreen : FoodScreen("mealPlanScreen", "Meal Plan", R.drawable.ic_meal_plan)
    data object BookmarkScreen : FoodScreen("bookmarkScreen", "Bookmark", R.drawable.ic_bookmarks)
    data object MenuScreen : FoodScreen("foodScreen", "Menu", R.drawable.ic_menu)

    data object RecipeSearch : FoodScreen("recipeSearch", "Recipe Search")
    data object RecipeScreen : FoodScreen("recipeScreen", "Recipe Screen")
    data object RecipeRandom : FoodScreen("recipeRandom", "Recipe Random")
    data object IngredientSearch : FoodScreen("ingredientSearch", "Ingredient Search")
    data object IngredientScreen : FoodScreen("ingredientScreen", "Ingredient Screen")
    data object ProductSearch : FoodScreen("productSearch", "Product Search")
    data object ProductScreen : FoodScreen("productScreen", "Product Screen")
    data object WineMenuScreen : FoodScreen("wineMenuScreen", "Wine Menu Screen")
    data object WineScreen : FoodScreen("wineScreen", "Wine Screen")
    data object WinePairSearch : FoodScreen("winePairSearch", "Wine Pair Search")
    data object WinePairScreen : FoodScreen("winePairScreen", "Wine Pair Screen")
    data object MiscScreen : FoodScreen("miscScreen", "Misc Screen")
    data object MiscClassifyImage : FoodScreen("miscClassifyImage", "Misc Classify Image")
    data object MiscAnalysisImage : FoodScreen("miscAnalysisImage", "Misc Analysis Image")
}