package com.sol.food.presentation.wine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sol.food.domain.model.recipe.CuisinesType
import com.sol.food.navigation.FoodScreen
import com.sol.food.utils.SearchBar

@Composable
fun WinePairSearch(navController: NavController) {
    var query by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 90.dp, start = 16.dp, end = 16.dp)
    ) {
        SearchBar(
            query = query,
            placeholder = "Search recipe...",
            onQueryChange = { query = it },
            onSearch = {
                navController.navigate(FoodScreen.WinePairScreen.route + "/${query}")
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Find a wine that goes well with a food. Food can be a dish name, an ingredient name, or a cuisine.",
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(CuisinesType.size) { index ->
                val cuisine = CuisinesType[index]
                CuisinesTypeItem(cuisine.displayName) {
                    navController.navigate(FoodScreen.WinePairScreen.route + "/${cuisine.name}")
                }
            }
        }

    }
}

@Composable
fun CuisinesTypeItem(cuisine: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = cuisine, style = MaterialTheme.typography.titleMedium)
        }
    }
}