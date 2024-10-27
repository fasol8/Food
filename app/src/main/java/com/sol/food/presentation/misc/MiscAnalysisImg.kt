package com.sol.food.presentation.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.sol.food.R
import com.sol.food.domain.model.misc.NutritionInfo
import com.sol.food.domain.model.misc.Recipe
import com.sol.food.domain.model.misc.UrlFoodImages
import com.sol.food.utils.ExpandableCard

@Composable
fun MiscAnalysisImg(miscViewModel: MiscViewModel = hiltViewModel()) {
    val imgAnalysis by miscViewModel.analysisImage.observeAsState()
    var urlText by remember { mutableStateOf("") }
    var isValidUrl by remember { mutableStateOf(true) }
    var imgUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 60.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Analysis Image", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = urlText,
                onValueChange = {
                    urlText = it
                    isValidUrl = android.util.Patterns.WEB_URL.matcher(urlText).matches()
                },
                label = { Text("Enter URL") },
                isError = !isValidUrl,
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (isValidUrl)
                        miscViewModel.getAnalysisImage(urlText)
                },
                enabled = isValidUrl && urlText.isNotEmpty(),
            ) {
                Text("Submit")
            }
        }
        if (!isValidUrl && urlText.isNotEmpty()) {
            Text(
                text = "Invalid URL",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (imgAnalysis != null) {
            AsyncImage(
                model = imgUrl,
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                placeholder = painterResource(R.drawable.no_image),
                error = painterResource(R.drawable.no_image)
            )
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Result", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = imgAnalysis?.category?.name ?: "??",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${imgAnalysis?.category?.probability ?: "??"}%",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(modifier = Modifier.heightIn(8.dp))
                    ExpandableCard(title = "Nutrition") {
                        Column {
                            Text(text = "Calories", style = MaterialTheme.typography.titleSmall)
                            NutritionItem(imgAnalysis!!.nutrition.calories)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Fat", style = MaterialTheme.typography.titleSmall)
                            NutritionItem(imgAnalysis!!.nutrition.fat)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Protein", style = MaterialTheme.typography.titleSmall)
                            NutritionItem(imgAnalysis!!.nutrition.protein)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Carbs", style = MaterialTheme.typography.titleSmall)
                            NutritionItem(imgAnalysis!!.nutrition.carbs)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Similar", style = MaterialTheme.typography.titleSmall)
                    LazyRow {
                        items(imgAnalysis!!.recipes.size) { index ->
                            val recipeImgA = imgAnalysis!!.recipes[index]
                            RecipeItem(recipeImgA) {}
                        }
                    }
                }
            }
        } else {
            Text(text = "Examples", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(24.dp))
            LazyRow(modifier = Modifier.heightIn(max = 600.dp)) {
                items(UrlFoodImages.entries.size) { index ->
                    val image = UrlFoodImages.entries[index].url
                    ImgAnalysisItem(image, miscViewModel) {
                        imgUrl = image
                    }
                }
            }
        }
    }
}

@Composable
fun NutritionItem(nutrition: NutritionInfo) {
    Column {
        Row {
            Text(
                text = "${nutrition.value} ${nutrition.unit}",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Standard Deviation: ${nutrition.standardDeviation}",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodySmall
            )
        }
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.White)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Confidence Range 95 Percent:",
                modifier = Modifier.weight(1.4f),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Min: ${nutrition.confidenceRange95Percent.min}",
                modifier = Modifier.weight(.8f),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Max: ${nutrition.confidenceRange95Percent.max}",
                modifier = Modifier.weight(.8f),
                style = MaterialTheme.typography.bodySmall
            )
        }
        HorizontalDivider(Modifier.fillMaxWidth(), thickness = 6.dp, color = Color.White)
    }
}

@Composable
fun RecipeItem(recipeImgA: Recipe, onClick: () -> Unit) {
    val imageBackground = ("https://img.spoonacular.com/recipes/${recipeImgA.id}-556x370.jpg") ?: ""

    Card(
        modifier = Modifier
            .width(350.dp)
            .height(180.dp)
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
                Text(text = recipeImgA.title ?: "", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun ImgAnalysisItem(image: String, miscViewModel: MiscViewModel, onClick: () -> Unit) {
    AsyncImage(
        model = image,
        contentDescription = "image",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                miscViewModel.getAnalysisImage(image)
                onClick()
            },
        placeholder = painterResource(R.drawable.no_image),
        error = painterResource(R.drawable.no_image)
    )
}
