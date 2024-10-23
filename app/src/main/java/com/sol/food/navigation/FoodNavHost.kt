package com.sol.food.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sol.food.presentation.bookmark.BookMarkScreen
import com.sol.food.presentation.home.HomeScreen
import com.sol.food.presentation.ingredient.IngredientScreen
import com.sol.food.presentation.mealPlan.MealPlanScreen
import com.sol.food.presentation.menu.MenuScreen
import com.sol.food.presentation.product.ProductScreen
import com.sol.food.presentation.recipe.RecipeScreen

@Composable
fun FoodNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = FoodScreen.HomeScreen.route) {
        composable(FoodScreen.HomeScreen.route) { HomeScreen(navController) }
        composable(FoodScreen.MealPlanScreen.route) { MealPlanScreen(navController) }
        composable(FoodScreen.BookmarkScreen.route) { BookMarkScreen() }
        composable(FoodScreen.MenuScreen.route) { MenuScreen(navController) }

        composable(FoodScreen.RecipeScreen.route) { RecipeScreen() }
        composable(FoodScreen.IngredientScreen.route) { IngredientScreen() }
        composable(FoodScreen.ProductScreen.route) { ProductScreen() }
    }
}