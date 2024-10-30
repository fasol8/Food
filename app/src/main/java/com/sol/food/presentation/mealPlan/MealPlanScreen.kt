package com.sol.food.presentation.mealPlan

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.sol.food.R
import com.sol.food.domain.model.mealPlan.DayMealData
import com.sol.food.domain.model.mealPlan.Meal
import com.sol.food.domain.model.mealPlan.Nutrients
import com.sol.food.domain.model.mealPlan.Week
import com.sol.food.navigation.FoodScreen
import com.sol.food.utils.ExpandableCard

@Composable
fun MealPlanScreen(
    navController: NavController,
    mealViewModel: MealPlanViewModel = hiltViewModel()
) {
    val mealGenerate by mealViewModel.generateMeal.observeAsState(emptyList())
    val mealGenerateWeek by mealViewModel.generateMealWeek.observeAsState()

    LaunchedEffect(true) {
        mealViewModel.getGenerateMealDay()
        mealViewModel.getGenerateMealWeek()
    }

    if (mealGenerate != null && mealGenerateWeek != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, bottom = 40.dp, start = 4.dp, end = 4.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            MealPlanTab(mealGenerate, mealGenerateWeek!!, mealViewModel, navController)
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun MealPlanTab(
    mealGenerate: List<Meal>,
    mealGenerateWeek: Week,
    mealViewModel: MealPlanViewModel,
    navController: NavController
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Day", "Week")

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFE0E0E0))
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .height(36.dp)
                        .width(tabPositions[selectedTabIndex].width * 0.9f)
                        .clip(RoundedCornerShape(18.dp))
                        .padding(horizontal = 4.dp)
                        .background(Color(0xFF102641))
                )
            },
            modifier = Modifier.fillMaxWidth(),
            divider = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        if (index == 0) mealViewModel.getGenerateMealDay() else mealViewModel.getGenerateMealWeek()
                    },
                    modifier = Modifier
                        .height(36.dp)
                        .zIndex(1f)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = if (selectedTabIndex == index) Color(0xFFA8E6CF) else Color(
                                    0xFF102641
                                ),
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.zIndex(2f)
                        )
                    }
                }
            }
        }
    }

    when (selectedTabIndex) {
        0 -> {
            MealDayTab(mealGenerate, navController)
        }

        1 -> {
            MealWeekTab(mealGenerateWeek, navController)
        }
    }
}

@Composable
fun MealDayTab(mealGenerate: List<Meal>, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .heightIn(max = 1000.dp)
            .padding(horizontal = 16.dp)
    ) {
        items(mealGenerate.size) { index ->
            val meal = mealGenerate[index]
            MealItem(meal = meal) {
                        navController.navigate(FoodScreen.RecipeScreen.route+"${meal.id}")
            }
        }
    }
}

@Composable
fun MealWeekTab(mealGenerateWeek: Week, navController: NavController) {

    val weekDays = listOf(
        "Monday" to DayMealData(mealGenerateWeek.monday.nutrients, mealGenerateWeek.monday.meals),
        "Tuesday" to DayMealData(
            mealGenerateWeek.tuesday.nutrients,
            mealGenerateWeek.tuesday.meals
        ),
        "Wednesday" to DayMealData(
            mealGenerateWeek.wednesday.nutrients,
            mealGenerateWeek.wednesday.meals
        ),
        "Thursday" to DayMealData(
            mealGenerateWeek.thursday.nutrients,
            mealGenerateWeek.thursday.meals
        ),
        "Friday" to DayMealData(mealGenerateWeek.friday.nutrients, mealGenerateWeek.friday.meals),
        "Saturday" to DayMealData(
            mealGenerateWeek.saturday.nutrients,
            mealGenerateWeek.saturday.meals
        ),
        "Sunday" to DayMealData(mealGenerateWeek.sunday.nutrients, mealGenerateWeek.sunday.meals)
    )

    weekDays.forEach { (dayName, dayData) ->
        ExpandableCard(title = dayName) {
            Column {
                NutrientResumeItem(dayData.nutrients)
                LazyColumn(modifier = Modifier.heightIn(max = 600.dp)) {
                    items(dayData.meals.size) { index ->
                        val meal = dayData.meals[index]
                        MealItem(meal = meal) {
                             navController.navigate(FoodScreen.RecipeScreen.route+"${meal.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NutrientResumeItem(nutrients: Nutrients) {
    Row {
        Text(
            text = "${nutrients.calories} Calories",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.White)
                .weight(1f)
        )
        Text(
            text = "${nutrients.protein} Protein",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.White)
                .weight(1f)
        )
        Text(
            text = "${nutrients.fat} Fat",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.White)
                .weight(1f)
        )
        Text(
            text = "${nutrients.carbohydrates} Carbs",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(4.dp)
                .border(1.dp, Color.White)
                .weight(1f)
        )
    }
}

@Composable
fun MealItem(meal: Meal, onClick: () -> Unit) {
    val imageBackground = ("https://img.spoonacular.com/recipes/${meal.id}-556x370.jpg") ?: ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(vertical = 8.dp),
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = meal.title ?: "", style = MaterialTheme.typography.titleLarge)
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
    }
}
