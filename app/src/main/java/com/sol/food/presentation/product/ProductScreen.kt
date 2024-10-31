package com.sol.food.presentation.product

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sol.food.R
import com.sol.food.domain.model.product.Ingredient
import com.sol.food.presentation.ingredient.NutrientItemIngredient
import com.sol.food.utils.ExpandableCard

@Composable
fun ProductScreen(idProduct: Int, productViewModel: ProductViewModel = hiltViewModel()) {
    val infoProduct by productViewModel.informationProduct.observeAsState()
    var isSaved by remember { mutableStateOf(false) }


    LaunchedEffect(idProduct) {
        productViewModel.getInformationProduct(idProduct)
    }

    if (infoProduct != null) {

        LaunchedEffect(infoProduct) {
            productViewModel.isProductSaved(infoProduct!!) { saved ->
                isSaved = saved
            }
        }

        val image = "https://img.spoonacular.com/products/${infoProduct?.id}-312x231.jpg"
        val importantBadges = infoProduct?.importantBadges

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(4.dp, vertical = 60.dp)
        ) {
            Box {
                AsyncImage(
                    model = image,
                    contentDescription = infoProduct?.title,
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
                            text = infoProduct?.title ?: "",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = infoProduct?.aisle ?: "",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray,
                        )
                        Row {
                            Row(modifier = Modifier.weight(1f)) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Ready in ...",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = "$${infoProduct?.price ?: "??"}",
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
                                    text = "${infoProduct?.servings?.number ?: "??"} ${infoProduct?.servings?.raw ?: ""}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (isSaved) {
                                        productViewModel.deleteItemById(infoProduct!!.id)
                                        isSaved = false
                                    } else {
                                        productViewModel.saveItem(infoProduct!!)
                                        isSaved = true
                                    }
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.Bottom)
                            ) {
                                Icon(
                                    painter = painterResource(if (isSaved) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark_border),
                                    contentDescription = "Save Product",
                                    tint = Color.White
                                )
                            }
                        }
                        if (importantBadges != null && importantBadges.isNotEmpty()) {
                            Text(
                                text = "Important Badges:${
                                    importantBadges.joinToString(separator = ", ").replace("_", " ")
                                }",
                                maxLines = 2
                            )
                        } else {
                            Text(text = "No important badges available")
                        }
                        ExpandableCard(title = "Ingredients") {
                            if (infoProduct?.ingredients != null) {
                                LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                                    items(infoProduct!!.ingredients.size) { index ->
                                        val ingredient = infoProduct!!.ingredients[index]
                                        IngredientItemProduct(ingredient = ingredient)
                                    }
                                }
                            } else {
                                Text(text = "No ingredients")
                            }
                        }
                        ExpandableCard(title = "Nutrients") {
                            LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                                items(infoProduct!!.nutrition.nutrients.size) { index ->
                                    val nutrient = infoProduct!!.nutrition.nutrients[index]
                                    NutrientItemIngredient(nutrient = nutrient, color = Color.Green)
                                }
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
fun IngredientItemProduct(ingredient: Ingredient) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Column {
            Text(
                text = ingredient.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            if (ingredient.description != null) {
                Text(
                    text = ingredient.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
            }
            Text(
                text = "Safety level: ${ingredient.safetyLevel ?: "Unknown"}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 2.dp, color = Color.White)
    }
}
