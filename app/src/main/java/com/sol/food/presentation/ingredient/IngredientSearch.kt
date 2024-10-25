package com.sol.food.presentation.ingredient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.sol.food.domain.model.ingredient.ResultSearch
import com.sol.food.navigation.FoodScreen
import com.sol.food.utils.SearchBar

@Composable
fun IngredientSearch(
    navController: NavController,
    viewModel: IngredientViewModel = hiltViewModel()
) {
    val ingredients by viewModel.searchIngredient.observeAsState()
    var query by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 40.dp)
    ) {
        SearchBar(query = query,
            placeholder = "Search ingredient ...",
            onQueryChange = { query = it },
            onSearch = {
                viewModel.getSearchIngredient(query)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (ingredients != null) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(ingredients!!.size) { index ->
                    val ingredient = ingredients!![index]
                    IngredientSearchItem(ingredient) {
                        navController.navigate(FoodScreen.IngredientScreen.route + "/${ingredient.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientSearchItem(ingredient: ResultSearch, onClick: () -> Unit) {
    val image = "https://spoonacular.com/cdn/ingredients_250x250/" + ingredient?.image

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    )
                )
                .paint(
                    painter = rememberAsyncImagePainter(image),
                    contentScale = ContentScale.Crop,
                    alpha = .3f
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = ingredient.name ?: "", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}