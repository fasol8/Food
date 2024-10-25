package com.sol.food.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sol.food.presentation.bookmark.BookMarkScreen
import com.sol.food.presentation.home.HomeScreen
import com.sol.food.presentation.ingredient.IngredientScreen
import com.sol.food.presentation.ingredient.IngredientSearch
import com.sol.food.presentation.mealPlan.MealPlanScreen
import com.sol.food.presentation.menu.MenuScreen
import com.sol.food.presentation.product.ProductScreen
import com.sol.food.presentation.product.ProductSearch
import com.sol.food.presentation.recipe.RecipeScreen
import com.sol.food.presentation.recipe.RecipeSearch
import com.sol.food.presentation.wine.WineMenu
import com.sol.food.presentation.wine.WinePairScreen
import com.sol.food.presentation.wine.WinePairSearch
import com.sol.food.presentation.wine.WineScreen

@Composable
fun FoodNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = FoodScreen.HomeScreen.route) {
        composable(FoodScreen.HomeScreen.route) { HomeScreen(navController) }
        composable(FoodScreen.MealPlanScreen.route) { MealPlanScreen(navController) }
        composable(FoodScreen.BookmarkScreen.route) { BookMarkScreen() }
        composable(FoodScreen.MenuScreen.route) { MenuScreen(navController) }


        composable(FoodScreen.RecipeSearch.route) { RecipeSearch(navController) }
        composable(FoodScreen.RecipeScreen.route) { RecipeScreen() }

        composable(FoodScreen.IngredientSearch.route) { IngredientSearch(navController) }
        composable(
            FoodScreen.IngredientScreen.route + "/{idIngredient}",
            arguments = listOf(navArgument("idIngredient") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("idIngredient") ?: return@composable
            IngredientScreen(id)
        }

        composable(FoodScreen.ProductSearch.route) { ProductSearch(navController) }
        composable(FoodScreen.ProductScreen.route + "/{idProduct}",
            arguments = listOf(navArgument("idProduct") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("idProduct") ?: return@composable
            ProductScreen(id)
        }

        composable(FoodScreen.WineMenuScreen.route) { WineMenu(navController) }
        composable(FoodScreen.WineScreen.route + "/{wine}",
            arguments = listOf(navArgument("wine") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val wineString = navBackStackEntry.arguments?.getString("wine") ?: return@composable
            WineScreen(wineString)
        }
        composable(FoodScreen.WinePairSearch.route) { WinePairSearch(navController) }
        composable(
            FoodScreen.WinePairScreen.route + "/{food}",
            arguments = listOf(navArgument("food") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val foodS = navBackStackEntry.arguments?.getString("food") ?: return@composable
            WinePairScreen(foodS)
        }
    }
}