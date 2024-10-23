package com.sol.food.presentation.home

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
fun HomeScreen(navController: NavController) {
    Column {
        Text(text = "Home screen", modifier = Modifier.padding(64.dp))
        Text(
            text = "Random",
            modifier = Modifier
                .padding(start = 64.dp)
                .clickable { navController.navigate(FoodScreen.RecipeScreen.route) })
    }
}