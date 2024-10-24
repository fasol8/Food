package com.sol.food.presentation.wine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sol.food.domain.model.wine.RecommendedWine
import kotlin.math.roundToInt

@Composable
fun WineScreen(wine: String, wineViewModel: WineViewModel = hiltViewModel()) {
    val wines by wineViewModel.recommendationWine.observeAsState()

    LaunchedEffect(wine) {
        wineViewModel.getRecommendationWine(wine.replace(" ", "_"))
    }

    if (wines != null) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 90.dp)
        ) {
            items(wines!!.recommendedWines.size) { index ->
                val wineR = wines!!.recommendedWines[index]
                WineItem(wineR)
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun WineItem(wine: RecommendedWine) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            AsyncImage(
                model = wine.imageUrl,
                contentDescription = wine.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(300.dp)
                    .width(100.dp)
            )
            Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                Text(
                    text = wine.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    Text(
                        text = wine.price,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "info",
                        color = Color.Blue,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable { /*TODO: intent url*/ })
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color.Yellow,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "${wine.averageRating.roundToInt() * 10}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Text(text = wine.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
