package com.sol.food.presentation.recipe

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.sol.food.R
import com.sol.food.domain.model.recipe.ExtendedIngredient
import com.sol.food.domain.model.recipe.NutrientGorB
import com.sol.food.domain.model.recipe.RecipeAttribute
import com.sol.food.domain.model.recipe.SimilarResponseItem
import com.sol.food.domain.model.recipe.Step
import com.sol.food.navigation.FoodScreen
import com.sol.food.utils.ExpandableCard
import kotlin.math.roundToInt

@Composable
fun RecipeScreen(
    idRecipe: Int,
    navController: NavController,
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    val recipe by recipeViewModel.informationRecipe.observeAsState()
    val recipeSimilar by recipeViewModel.similarRecipe.observeAsState(emptyList())
    val recipeNutrient by recipeViewModel.nutrientRecipe.observeAsState()
    val context = LocalContext.current
    var isSaved by remember { mutableStateOf(false) }

    LaunchedEffect(idRecipe) {
        recipeViewModel.getInformationRecipe(idRecipe)
    }

    LaunchedEffect(recipe) {
        if (recipe != null) {
            recipeViewModel.loadData(recipe!!.id)
            recipeViewModel.isRecipeSaved(recipe!!) { saved ->
                isSaved = saved
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(4.dp, top = 40.dp, bottom = 90.dp)
    ) {
        Box {
            AsyncImage(
                model = recipe?.image,
                contentDescription = recipe?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(370.dp),
                placeholder = painterResource(R.drawable.no_image),
                error = painterResource(R.drawable.no_image)
            )
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
                                text = recipe?.title ?: "Unknown",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "By ${recipe?.sourceName ?: ""}",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(recipe!!.sourceUrl))
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
                        Text(text = recipe?.spoonacularScore?.roundToInt().toString() ?: "0")
                        Spacer(modifier = Modifier.width(2.dp))
                        IconButton(
                            onClick = {
                                if (isSaved) {
                                    recipeViewModel.deleteItemById(recipe!!.id)
                                    isSaved = false
                                } else {
                                    recipeViewModel.saveItem(recipe!!)
                                    isSaved = true
                                }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                painter = painterResource(if (isSaved) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark_border),
                                contentDescription = "Save Recipe",
                                tint = Color.White
                            )
                        }
                    }
                    Row {
                        val attributes = listOf(
                            RecipeAttribute(recipe?.cheap == true, R.drawable.cheap, "Cheap"),
                            RecipeAttribute(
                                recipe?.dairyFree == true,
                                R.drawable.dairyfree,
                                "Dairy Free"
                            ),
                            RecipeAttribute(
                                recipe?.glutenFree == true,
                                R.drawable.glutenfree,
                                "Gluten Free"
                            ),
                            RecipeAttribute(
                                recipe?.lowFodmap == true,
                                R.drawable.lowfodmap,
                                "Low Fodmap"
                            ),
                            RecipeAttribute(
                                recipe?.sustainable == true,
                                R.drawable.sustainable,
                                "Sustainable"
                            ),
                            RecipeAttribute(recipe?.vegan == true, R.drawable.vegan, "Vegan"),
                            RecipeAttribute(
                                recipe?.vegetarian == true,
                                R.drawable.vegetarian,
                                "Vegetarian"
                            ),
                            RecipeAttribute(
                                recipe?.veryHealthy == true,
                                R.drawable.veryhealthy,
                                "Very Healthy"
                            ),
                            RecipeAttribute(
                                recipe?.veryPopular == true,
                                R.drawable.verypopular,
                                "Very Popular"
                            )
                        )

                        attributes.filter { it.isEnabled }.forEach { attribute ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painter = painterResource(id = attribute.iconRes),
                                    contentDescription = attribute.label,
                                    modifier = Modifier
                                        .height(40.dp)
                                        .width(40.dp)
                                        .padding(4.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = attribute.label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Light
                                )
                            }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Price Serving",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "$${recipe?.pricePerServing ?: "0"}",
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
                            text = "${recipe?.readyInMinutes ?: "??"} min",
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
                            text = "${recipe?.servings ?: "??"} service",
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = " Description", style = MaterialTheme.typography.titleSmall)
                    val summary =
                        if (recipe?.summary != null) HtmlCompat.fromHtml(
                            recipe!!.summary, HtmlCompat.FROM_HTML_MODE_LEGACY
                        ) else ""
                    Text(
                        text = summary.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                ExpandableCard(title = "Ingredients") {
                    if (recipe?.extendedIngredients != null) {
                        LazyColumn(modifier = Modifier.heightIn(max = 400.dp)) {
                            items(recipe!!.extendedIngredients.size) { index ->
                                val ingredient = recipe!!.extendedIngredients[index]
                                IngredientItemRecipe(ingredient) {
                                    navController.navigate(FoodScreen.IngredientScreen.route + "/${ingredient.id}")
                                }
                            }
                        }
                    } else {
                        Text(text = "No ingredients")
                    }
                }
                ExpandableCard(title = "Instructions") {
                    if (recipe?.analyzedInstructions != null) {
                        Column {
                            LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                                items(recipe!!.analyzedInstructions.size) { index ->
                                    val instruction =
                                        recipe!!.analyzedInstructions[0].steps[index]
                                    InstructionItem(instruction)
                                }
                            }
                            Text(
                                text = "Read the detailed instructions on ${recipe?.sourceName ?: "??"}",
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    val intent =
                                        Intent(Intent.ACTION_VIEW, Uri.parse(recipe!!.sourceUrl))
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
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(1.dp, Color.White)
                                        .weight(1f)
                                )
                                Text(
                                    text = "${recipeNutrient!!.protein} Protein",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(1.dp, Color.White)
                                        .weight(1f)
                                )
                                Text(
                                    text = "${recipeNutrient!!.fat} Fat",
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(1.dp, Color.White)
                                        .weight(1f)
                                )
                                Text(
                                    text = "${recipeNutrient!!.carbs} Carbs",
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
                        val recipeS = recipeSimilar[index]
                        RecipeSimilarItem(recipeS) {
                            navController.navigate(FoodScreen.RecipeScreen.route + "/${recipeS.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientItemRecipe(ingredient: ExtendedIngredient, onClick: () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onClick() }) {
            val image =
                "https://img.spoonacular.com/ingredients_100x100/${ingredient.name}.jpg" ?: ""
            AsyncImage(
                model = image,
                contentDescription = ingredient.name,
                modifier = Modifier
                    .weight(1f),
                placeholder = painterResource(R.drawable.no_image),
                error = painterResource(R.drawable.no_image)
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
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 2.dp, color = Color.White)
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
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 2.dp, color = Color.White)
    }
}

@Composable
fun NutrientItem(nutrient: NutrientGorB, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = nutrient.title, style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1.4f)
        )
        Text(
            text = nutrient.amount,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(10.dp)
                .background(Color.Transparent, shape = RoundedCornerShape(5.dp))
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

@Composable
fun RecipeSimilarItem(recipe: SimilarResponseItem, onClick: () -> Unit) {
    val imageBackground = ("https://img.spoonacular.com/recipes/${recipe.id}-556x370.jpg") ?: ""

    Card(
        modifier = Modifier
            .width(350.dp)
            .height(200.dp)
            .padding(8.dp),
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
                    painter = rememberAsyncImagePainter(model = imageBackground),
                    contentScale = ContentScale.Crop,
                    alpha = .3f
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = recipe.title ?: "", style = MaterialTheme.typography.titleLarge)
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = "Ready in ...",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "${recipe.readyInMinutes} min" ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_service),
                        contentDescription = "Serving",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = recipe.servings.toString() ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}