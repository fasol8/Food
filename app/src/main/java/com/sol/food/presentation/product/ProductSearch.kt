package com.sol.food.presentation.product

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
import com.sol.food.domain.model.product.ProductSearch
import com.sol.food.navigation.FoodScreen
import com.sol.food.utils.SearchBar

@Composable
fun ProductSearch(navController: NavController, viewModel: ProductViewModel = hiltViewModel()) {
    val products by viewModel.searchProduct.observeAsState()
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
                viewModel.getSearchProduct(query)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (products != null) {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(products!!.size) { index ->
                    val product = products!![index]
                    ProductSearchItem(product) {
                        navController.navigate(FoodScreen.ProductScreen.route + "/${product.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductSearchItem(product: ProductSearch, onClick: () -> Unit) {
    val image = "https://img.spoonacular.com/products/${product.id}-312x231.jpg"

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
                Text(text = product.title ?: "", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}