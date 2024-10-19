package com.sol.food.presentation.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun RecipeScreen(recipeViewModel: RecipeViewModel = hiltViewModel()) {
    val recipeRandom by recipeViewModel.randomRecipe.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp, top = 60.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Column {
                AsyncImage(model = recipeRandom?.image, contentDescription = recipeRandom?.title)
                Text(
                    text = recipeRandom?.title ?: "Unknown",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}