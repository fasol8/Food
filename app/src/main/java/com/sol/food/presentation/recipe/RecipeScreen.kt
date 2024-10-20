package com.sol.food.presentation.recipe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sol.food.domain.model.recipe.ExtendedIngredient
import com.sol.food.domain.model.recipe.NutrientX
import com.sol.food.domain.model.recipe.Step
import com.sol.food.utils.colorCardType
import kotlin.math.roundToInt

@Composable
fun RecipeScreen(recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val recipeRandom by recipeViewModel.randomRecipe.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(4.dp, vertical = 60.dp)
    ) {
        Box() {
            AsyncImage(
                model = recipeRandom?.image,
                contentDescription = recipeRandom?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
//                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
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
                .fillMaxWidth(),
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
                                text = recipeRandom?.sourceName ?: "Unknown",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Blue,
                                modifier = Modifier.clickable { /*TODO: intent url*/ }
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
                            contentDescription = "Rating",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = recipeRandom?.pricePerServing.toString() ?: "0",
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Ready time",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = recipeRandom?.readyInMinutes.toString() ?: "??",
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = "Rating",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(text = recipeRandom?.servings.toString() ?: "??", color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = " Description", style = MaterialTheme.typography.titleSmall)
                    val summary =
                        if (recipeRandom?.summary != null) HtmlCompat.fromHtml(
                            recipeRandom!!.summary, HtmlCompat.FROM_HTML_MODE_LEGACY
                        ) else ""
                    Text(
                        text = summary.toString(),
//                        maxLines = 8,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                ExpandableCard(title = "Ingredients") {
                    if (recipeRandom?.extendedIngredients != null) {
                        LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                            items(recipeRandom!!.extendedIngredients.size) { index ->
                                val ingredient = recipeRandom!!.extendedIngredients[index]
                                IngredientItem(ingredient)
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
                                modifier = Modifier.clickable { /*TODO:intent url*/ })
                        }
                    } else {
                        Text(text = "No instructions")
                    }
                }
                ExpandableCard(title = "Nutritional Information") {
                    if (recipeRandom?.nutrition?.nutrients != null) {
                        LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                            items(recipeRandom!!.nutrition.nutrients.size) { index ->
                                val nutrient = recipeRandom!!.nutrition.nutrients[index]
                                val color = if (index < 10) Color.Red else Color.Blue
                                NutrientItem(nutrient, color)
                            }
                        }
//                        Text(text = "Limit These", color=Color.Red)
//                        Text(text = "Get Enough Of These", color=Color.Blue)
                    } else {
                        Text(text = "No nutrients info")
                    }
                }
            }
        }

    }
}

@Composable
fun ExpandableCard(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorCardType(title),
            contentColor = Color.White,
            disabledContainerColor = colorCardType(title),
            disabledContentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                content()
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: ExtendedIngredient) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val image = "https://img.spoonacular.com/ingredients_100x100/${ingredient.image}"
            AsyncImage(
                model = image,
                contentDescription = ingredient.name,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "${ingredient.amount} ${ingredient.unit}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = ingredient.name, style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
        }
        Divider(Modifier.fillMaxWidth(), color = Color.White, thickness = 2.dp)
    }
}

@Composable
fun InstructionItem(instruction: Step) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = instruction.number.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .border(1.dp, color = Color.Blue, shape = RoundedCornerShape(50.dp))
                    .padding(4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = instruction.step, style = MaterialTheme.typography.bodyMedium,
            )
        }
        Divider(Modifier.fillMaxWidth(), color = Color.White, thickness = 2.dp)
    }
}

@Composable
fun NutrientItem(nutrient: NutrientX, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = nutrient.name, style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1.4f)
        )
        Text(
            text = "${nutrient.amount}${nutrient.unit}",
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(10.dp)
                .background(Color.Cyan, shape = RoundedCornerShape(5.dp))
                .weight(1.8f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = nutrient.percentOfDailyNeeds.roundToInt() / 100f)
                    .height(10.dp)
                    .background(color, shape = RoundedCornerShape(5.dp))
            )
        }
        Text(
            text = "${nutrient.percentOfDailyNeeds}%",
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            modifier = Modifier.weight(.8f)
        )

    }
}
