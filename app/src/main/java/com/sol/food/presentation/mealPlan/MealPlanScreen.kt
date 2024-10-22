package com.sol.food.presentation.mealPlan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sol.food.R
import com.sol.food.domain.model.mealPlan.Meal

@Composable
fun MealPlanScreen(viewModel: MealPlanViewModel = hiltViewModel()) {
    val mealGenerate by viewModel.generateMeal.observeAsState()

    Column {
        Text(text = "Meal Plan screen", modifier = Modifier.padding(64.dp))
        if (mealGenerate != null) {
            LazyColumn {
                items(mealGenerate!!.meals.size) { index ->
                    val meal = mealGenerate!!.meals[index]
                    MealItem(meal = meal) {}
                }
            }
        }
    }
}

@Composable
fun MealItem(meal: Meal, onClick: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        onClick = { onClick() }
    ) {
        Column {
            Text(text = meal.title ?: "", style = MaterialTheme.typography.titleLarge)
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                contentDescription = "Ready in ...",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${meal.readyInMinutes} min" ?: "",
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
                text = meal.servings.toString() ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
