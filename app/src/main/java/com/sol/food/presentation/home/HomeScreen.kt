package com.sol.food.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.sol.food.R
import com.sol.food.domain.model.recipe.MealType
import com.sol.food.navigation.FoodScreen
import com.sol.food.presentation.recipe.RecipeSearchItem
import com.sol.food.presentation.recipe.RecipeSimilarItem
import com.sol.food.presentation.recipe.RecipeViewModel
import com.sol.food.utils.SearchBar

@Composable
fun HomeScreen(navController: NavController, recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val recipe by recipeViewModel.randomRecipe.observeAsState()
    val recipeSearch by recipeViewModel.searchRecipe.observeAsState()
    val recipeSimilar by recipeViewModel.similarRecipe.observeAsState(emptyList())
    var query by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        recipeViewModel.getRandomRecipe()
    }

    LaunchedEffect(recipe) {
        if (recipe != null)
            recipeViewModel.loadData(recipe!!.id)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, bottom = 90.dp)
    ) {
        SearchBar(
            query = query,
            placeholder = "Search recipe...",
            onQueryChange = { query = it },
            onSearch = {
                recipeViewModel.getSearchRecipe(query)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Get cooking today!",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (query.isEmpty()) {
            if (recipe != null) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Card(onClick = {
                        navController.navigate(FoodScreen.RecipeScreen.route + "/${recipe!!.id}")
                    }) {
                        Column {
                            AsyncImage(
                                model = recipe?.image, contentDescription = recipe?.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(290.dp),
                                placeholder = painterResource(R.drawable.no_image),
                                error = painterResource(R.drawable.no_image)
                            )
                            Text(
                                text = recipe?.title ?: "",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                    bottom = 4.dp
                                )
                            )
                        }
                    }
                    Text(
                        text = "Search by Meal Type",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                    )
                    LazyRow {
                        items(MealType.size) { index ->
                            val mealType = MealType[index]
                            MealTypeItem(mealType) {
                                query = mealType.displayName
                                recipeViewModel.getSearchRecipe(query)
                            }
                        }
                    }
                    Text(
                        text = "Similar",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp)
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
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } else {
            recipeSearch?.let { searchResults ->
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(searchResults.size) { index ->
                        val searchRecipe = searchResults[index]
                        RecipeSearchItem(searchRecipe) {
                            navController.navigate(FoodScreen.RecipeScreen.route + "/${searchRecipe.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MealTypeItem(meal: MealType, onClick: () -> Unit) {
    Card(onClick = { onClick() }, modifier = Modifier.padding(4.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(meal.icon),
                contentDescription = meal.displayName,
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .padding(top = 8.dp)
            )
            Text(text = meal.displayName, modifier = Modifier.padding(8.dp))
        }
    }
}