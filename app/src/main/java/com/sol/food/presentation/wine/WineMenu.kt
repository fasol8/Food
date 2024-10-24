package com.sol.food.presentation.wine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sol.food.domain.model.wine.WineType
import com.sol.food.navigation.FoodScreen

@Composable
fun WineMenu(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(top = 40.dp, bottom = 90.dp)
    ) {
        items(WineType.size) { index ->
            val wineT = WineType[index]
            WineTypeItem(wineT.displayName) {
                navController.navigate(FoodScreen.WineScreen.route + "/${wineT.name}")
            }
        }
    }
}

@Composable
fun WineTypeItem(wine: String, onclick: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(8.dp), onClick = { onclick() }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Text(text = wine, style = MaterialTheme.typography.titleMedium)
        }
    }
}
