package com.sol.food.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainBar() {
    val navController = rememberNavController()

    val items = listOf(
        FoodScreen.HomeScreen,
        FoodScreen.MealPlanScreen,
        FoodScreen.BookmarkScreen,
        FoodScreen.MenuScreen
    )

    Scaffold(bottomBar = { BottomBar(navController, items) }) {
        FoodNavHost(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController, items: List<FoodScreen>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.label
                    )
                },
//                label = { Text(text = screen.label) }
            )
        }
    }
}
