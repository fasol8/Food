package com.sol.food.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sol.food.domain.model.misc.MiscType
import com.sol.food.presentation.bookmark.BookMarkScreen
import com.sol.food.presentation.home.HomeScreen
import com.sol.food.presentation.home.SplashScreen
import com.sol.food.presentation.ingredient.IngredientScreen
import com.sol.food.presentation.ingredient.IngredientSearch
import com.sol.food.presentation.mealPlan.MealPlanScreen
import com.sol.food.presentation.menu.MenuScreen
import com.sol.food.presentation.misc.MiscAnalysisImg
import com.sol.food.presentation.misc.MiscClassifyImg
import com.sol.food.presentation.misc.MiscScreen
import com.sol.food.presentation.product.ProductScreen
import com.sol.food.presentation.product.ProductSearch
import com.sol.food.presentation.recipe.RecipeRandom
import com.sol.food.presentation.recipe.RecipeScreen
import com.sol.food.presentation.recipe.RecipeSearch
import com.sol.food.presentation.wine.WineMenu
import com.sol.food.presentation.wine.WinePairScreen
import com.sol.food.presentation.wine.WinePairSearch
import com.sol.food.presentation.wine.WineScreen

@Composable
fun FoodNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = FoodScreen.SplashScreen.route) {
        composable(FoodScreen.SplashScreen.route) { SplashScreen(navController) }
        composable(FoodScreen.HomeScreen.route) { HomeScreen(navController) }
        composable(FoodScreen.MealPlanScreen.route) { MealPlanScreen(navController) }
        composable(FoodScreen.BookmarkScreen.route) { BookMarkScreen(navController) }
        composable(FoodScreen.MenuScreen.route) { MenuScreen(navController) }


        composable(FoodScreen.RecipeSearch.route) { RecipeSearch(navController) }
        composable(
            FoodScreen.RecipeScreen.route + "/{idRecipe}",
            arguments = listOf(navArgument("idRecipe") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("idRecipe") ?: return@composable
            RecipeScreen(id, navController)
        }
        composable(FoodScreen.RecipeRandom.route) { RecipeRandom(navController) }

        composable(FoodScreen.IngredientSearch.route) { IngredientSearch(navController) }
        composable(
            FoodScreen.IngredientScreen.route + "/{idIngredient}",
            arguments = listOf(navArgument("idIngredient") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("idIngredient") ?: return@composable
            IngredientScreen(id)
        }

        composable(FoodScreen.ProductSearch.route) { ProductSearch(navController) }
        composable(
            FoodScreen.ProductScreen.route + "/{idProduct}",
            arguments = listOf(navArgument("idProduct") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("idProduct") ?: return@composable
            ProductScreen(id)
        }

        composable(FoodScreen.WineMenuScreen.route) { WineMenu(navController) }
        composable(
            FoodScreen.WineScreen.route + "/{wine}",
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

        composable(
            FoodScreen.MiscScreen.route + "/{miscType}",
            arguments = listOf(navArgument("miscType") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val typeString = navBackStackEntry.arguments?.getString("miscType") ?: return@composable
            val type = MiscType.fromString(typeString) ?: return@composable
            MiscScreen(type)
        }
        composable(FoodScreen.MiscClassifyImage.route) { MiscClassifyImg() }
        composable(FoodScreen.MiscAnalysisImage.route) { MiscAnalysisImg() }
    }
}