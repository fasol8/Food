package com.sol.food.presentation.recipe

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sol.food.R
import com.sol.food.navigation.FoodScreen
import com.sol.food.utils.ExpandableCard
import kotlin.math.roundToInt

@Composable
fun RecipeRandom(navController: NavController, recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val recipeRandom by recipeViewModel.randomRecipe.observeAsState()
    val recipeSimilar by recipeViewModel.similarRecipe.observeAsState(emptyList())
    val recipeNutrient by recipeViewModel.nutrientRecipe.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(true) {
        recipeViewModel.getRandomRecipe()
    }

    LaunchedEffect(recipeRandom) {
        if (recipeRandom != null)
            recipeViewModel.loadData(recipeRandom!!.id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(4.dp, top = 40.dp, bottom = 90.dp)
    ) {
        Box {
            AsyncImage(
                model = recipeRandom?.image,
                contentDescription = recipeRandom?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(370.dp),
                placeholder = painterResource(R.drawable.no_image),
                error = painterResource(R.drawable.no_image)
            )
            IconButton(
                onClick = { /*TODO: save recipe*/ },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Save Recipe",
                    tint = Color.White
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-20).dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
        ) {
            Column {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = recipeRandom?.title ?: "Unknown",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "By ${recipeRandom?.sourceName ?: ""}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(recipeRandom?.sourceUrl)
                                    )
                                    context.startActivity(intent)
                                }
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color.Yellow,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(text = recipeRandom?.spoonacularScore?.roundToInt().toString() ?: "0")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Price Serving",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "$${recipeRandom?.pricePerServing ?: "0"}",
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_time),
                            contentDescription = "Ready time",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "${recipeRandom?.readyInMinutes ?: "??"} min",
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_service),
                            contentDescription = "Serving",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "${recipeRandom?.servings ?: "??"} service",
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = " Description", style = MaterialTheme.typography.titleSmall)
                    val summary =
                        if (recipeRandom?.summary != null) HtmlCompat.fromHtml(
                            recipeRandom!!.summary, HtmlCompat.FROM_HTML_MODE_LEGACY
                        ) else ""
                    Text(
                        text = summary.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                ExpandableCard(title = "Ingredients") {
                    if (recipeRandom?.extendedIngredients != null) {
                        LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                            items(recipeRandom!!.extendedIngredients.size) { index ->
                                val ingredient = recipeRandom!!.extendedIngredients[index]
                                IngredientItemRecipe(ingredient)
                            }
                        }
                    } else {
                        Text(text = "No ingredients")
                    }
                }
                ExpandableCard(title = "Instructions") {
                    if (recipeRandom?.analyzedInstructions != null) {
                        Column {
                            LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                                items(recipeRandom!!.analyzedInstructions.size) { index ->
                                    val instruction =
                                        recipeRandom!!.analyzedInstructions[0].steps[index]
                                    InstructionItem(instruction)
                                }
                            }
                            Text(
                                text = "Read the detailed instructions on ${recipeRandom!!.sourceName}",
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(recipeRandom?.sourceUrl)
                                    )
                                    context.startActivity(intent)
                                })
                        }
                    } else {
                        Text(text = "No instructions")
                    }
                }
                ExpandableCard(title = "Nutritional Information") {
                    if (recipeNutrient != null) {
                        Column {
                            Row {
                                Text(
                                    text = "${recipeNutrient!!.calories} Calories",
//                                style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(1.dp, Color.White)
                                        .weight(1f)
                                )
                                Text(
                                    text = "${recipeNutrient!!.protein} Protein",
//                                style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(1.dp, Color.White)
                                        .weight(1f)
                                )
                                Text(
                                    text = "${recipeNutrient!!.fat} Fat",
//                                style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(1.dp, Color.White)
                                        .weight(1f)
                                )
                                Text(
                                    text = "${recipeNutrient!!.carbs} Carbs",
//                                style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(1.dp, Color.White)
                                        .weight(1f)
                                )
                            }
                            Text(text = "Limit These", color = Color.Red)
                            LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                                items(recipeNutrient!!.bad.size) { index ->
                                    val nutrient = recipeNutrient!!.bad[index]
                                    NutrientItem(nutrient = nutrient, color = Color.Red)
                                }
                            }
                            Text(text = "Get Enough Of These", color = Color.Blue)
                            LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                                items(recipeNutrient!!.good.size) { index ->
                                    val nutrient = recipeNutrient!!.good[index]
                                    NutrientItem(nutrient = nutrient, color = Color.Blue)
                                }
                            }
                        }
                    } else {
                        Text(text = "No nutrients info")
                    }
                }
                Text(
                    text = "Similar",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
                LazyRow {
                    items(recipeSimilar.size) { index ->
                        val recipe = recipeSimilar[index]
                        RecipeSimilarItem(recipe) {
                            navController.navigate(FoodScreen.RecipeScreen.route + "/${recipe.id}")
                        }
                    }
                }
            }
        }

    }
}
