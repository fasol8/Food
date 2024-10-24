package com.sol.food.presentation.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
        Text(
            text = "Ingredient screen",
            modifier = Modifier
                .padding(64.dp)
                .clickable { navController.navigate(FoodScreen.IngredientScreen.route) })
        Text(
            text = "Product screen",
            modifier = Modifier
                .padding(64.dp)
                .clickable { navController.navigate(FoodScreen.ProductScreen.route) })
        Text(
            text = "Wine screen",
            modifier = Modifier
                .padding(64.dp)
                .clickable { navController.navigate(FoodScreen.WineMenuScreen.route) })
    }
}
