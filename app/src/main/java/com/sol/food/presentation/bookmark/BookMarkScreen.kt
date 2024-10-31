package com.sol.food.presentation.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.sol.food.data.local.ItemEntity
import com.sol.food.navigation.FoodScreen

@Composable
fun BookMarkScreen(
    navController: NavController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val itemsBook by bookmarkViewModel.items.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        bookmarkViewModel.getAllItems()
    }

    val types = listOf("Recipe", "Product", "Ingredient")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, top = 32.dp)
    ) {
        items(types) { type ->
            val filteredItems = itemsBook.filter { it.type == type }
            if (filteredItems.isNotEmpty()) {
                Card(modifier = Modifier.padding(8.dp)) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = type,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
                        )
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            items(filteredItems) { itemBook ->
                                ItemBookItem(itemBook) {
                                    val route = when (type) {
                                        "Recipe" -> FoodScreen.RecipeScreen.route
                                        "Product" -> FoodScreen.ProductScreen.route
                                        "Ingredient" -> FoodScreen.IngredientScreen.route
                                        else -> null
                                    }
                                    route?.let {
                                        navController.navigate("$it/${itemBook.id}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemBookItem(item: ItemEntity, onClick: () -> Unit) {
    Card(
        onClick = { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .width(300.dp)
            .height(170.dp)
            .padding(8.dp)
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
                    painter = rememberAsyncImagePainter(model = item.image),
                    contentScale = ContentScale.Crop,
                    alpha = .3f
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = item.name ?: "", style = MaterialTheme.typography.titleLarge)

            }
        }
    }
}
