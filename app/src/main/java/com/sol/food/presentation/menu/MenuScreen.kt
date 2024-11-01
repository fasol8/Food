package com.sol.food.presentation.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sol.food.R
import com.sol.food.domain.model.misc.MiscType
import com.sol.food.navigation.FoodScreen
import com.sol.food.utils.ExpandableItemCard

@Composable
fun MenuScreen(navController: NavController) {
    val items = listOf(
        MenuItem("Ingredient", R.drawable.ic_ingredient) {
            Text(
                text = "Ingredient Search",
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .clickable { navController.navigate(FoodScreen.IngredientSearch.route) }
            )
        },
        MenuItem("Product", R.drawable.ic_product) {
            Text(
                text = "Product Search",
                modifier = Modifier
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .clickable { navController.navigate(FoodScreen.ProductSearch.route) }
            )
        },
        MenuItem("Recipe", R.drawable.ic_recipe) {
            Column {
                Text(
                    text = "Recipe Search",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.RecipeSearch.route) }
                )
                Text(
                    text = "Recipe Random",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.RecipeRandom.route) }
                )
            }
        },
        MenuItem("Wine", R.drawable.ic_wine) {
            Column {
                Text(
                    text = "Wine Menu",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.WineMenuScreen.route) }
                )
                Text(
                    text = "Wine Pair",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.WinePairSearch.route) }
                )
            }
        },
        MenuItem("Misc", R.drawable.ic_misc) {
            Column {
                Text(
                    text = "Random Joke",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.MiscScreen.route + "/${MiscType.Joke.typeName}") }
                )
                Text(
                    text = "Random Trivia",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.MiscScreen.route + "/${MiscType.Trivia.typeName}") }
                )
            }
        },
        MenuItem("Image", R.drawable.ic_image_search) {
            Column {
                Text(
                    text = "Classify Image",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.MiscClassifyImage.route) }
                )
                Text(
                    text = "Analysis Image",
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        .clickable { navController.navigate(FoodScreen.MiscAnalysisImage.route) }
                )
            }
        }
    )

    Column {
        Text(
            text = "Menu",
            modifier = Modifier.padding(16.dp, top = 40.dp),
            style = MaterialTheme.typography.titleLarge,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.size) { index ->
                val item = items[index]
                ExpandableItemCard(
                    title = item.title,
                    icon = painterResource(item.icon),
                    content = item.content
                )
            }
        }
    }
}

data class MenuItem(
    val title: String,
    val icon: Int,
    val content: @Composable () -> Unit
)