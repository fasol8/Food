package com.sol.food.presentation.ingredient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sol.food.R
import com.sol.food.domain.model.ingredient.Nutrient
import com.sol.food.domain.model.ingredient.Property
import com.sol.food.utils.ExpandableCard
import kotlin.math.roundToInt

@Composable
fun IngredientScreen(
    idIngredient: Int,
    ingredientViewModel: IngredientViewModel = hiltViewModel()
) {
    LaunchedEffect(idIngredient) {
        ingredientViewModel.getInformationIngredient(idIngredient)
    }

    val infoIngredient by ingredientViewModel.informationIngredient.observeAsState()
    val image = "https://spoonacular.com/cdn/ingredients_250x250/" + infoIngredient?.image
    val possibleUnits = infoIngredient?.possibleUnits
    var isSaved by remember { mutableStateOf(false) }

    if (infoIngredient != null) {

        LaunchedEffect(infoIngredient) {
            ingredientViewModel.isIngredientSaved(infoIngredient!!) { saved ->
                isSaved = saved
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(4.dp, vertical = 60.dp)
        ) {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = infoIngredient?.name,
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
                        Text(
                            text = infoIngredient?.name ?: "",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = infoIngredient?.originalName ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray,
                                )
                                Text(
                                    text = "consistency: ${infoIngredient?.consistency ?: "??"}",
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (isSaved) {
                                        ingredientViewModel.deleteItemById(infoIngredient!!.id)
                                        isSaved = false
                                    } else {
                                        ingredientViewModel.saveItem(infoIngredient!!)
                                        isSaved = true
                                    }
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Icon(
                                    painter = painterResource(if (isSaved) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark_border),
                                    contentDescription = "Save Ingredient",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        if (possibleUnits != null && possibleUnits.isNotEmpty()) {
                            Text(
                                text = "possibleUnits: ${possibleUnits.joinToString(", ")}",
                                maxLines = 2
                            )
                        } else {
                            Text(text = "No possible units available")
                        }
                    }
                    ExpandableCard(title = "Nutrients") {
                        LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                            items(infoIngredient!!.nutrition.nutrients.size) { index ->
                                val nutrient = infoIngredient!!.nutrition.nutrients[index]
                                NutrientItemIngredient(nutrient = nutrient, color = Color.Green)
                            }
                        }
                    }
                    ExpandableCard(title = "Properties") {
                        LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                            items(infoIngredient!!.nutrition.properties.size) { index ->
                                val property = infoIngredient!!.nutrition.properties[index]
                                PropertyItemIngredient(property = property)
                            }
                        }
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun NutrientItemIngredient(nutrient: Nutrient, color: Color) {
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
fun PropertyItemIngredient(property: Property) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = property.name, style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${property.amount} ${property.unit ?: ""}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )

    }
}