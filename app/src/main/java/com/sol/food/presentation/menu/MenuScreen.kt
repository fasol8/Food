package com.sol.food.presentation.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sol.food.navigation.FoodScreen

@Composable
fun MenuScreen(navController: NavController) {
    Column {
        Text(text = "Menu screen", modifier = Modifier.padding(64.dp))
        Row {
            Text(
                text = "Ingredient Search",
                modifier = Modifier
                    .padding(start = 64.dp, top = 8.dp, bottom = 8.dp)
                    .clickable { navController.navigate(FoodScreen.IngredientSearch.route) })
            Text(
                text = "Ingredient screen",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate(FoodScreen.IngredientScreen.route) })
        }
        Row {
            Text(
                text = "Product Search",
                modifier = Modifier
                    .padding(start = 64.dp, top = 8.dp, bottom = 8.dp)
                    .clickable { navController.navigate(FoodScreen.ProductSearch.route) })
            Text(
                text = "Product screen",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate(FoodScreen.ProductScreen.route) })
        }
        Row {
            Text(
                text = "Wine Menu screen",
                modifier = Modifier
                    .padding(start = 64.dp, top = 8.dp, bottom = 8.dp)
                    .clickable { navController.navigate(FoodScreen.WineMenuScreen.route) })
            Text(
                text = "Wine Pair screen",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { navController.navigate(FoodScreen.WinePairSearch.route) })
        }
        Text(
            text = "Recipe search screen",
            modifier = Modifier
                .padding(64.dp)
                .clickable { navController.navigate(FoodScreen.RecipeSearch.route) })
    }
}
