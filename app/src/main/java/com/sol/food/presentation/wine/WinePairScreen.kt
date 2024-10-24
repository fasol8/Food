package com.sol.food.presentation.wine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun WinePairScreen(food: String, wineViewModel: WineViewModel = hiltViewModel()) {
    val winePair by wineViewModel.pairWine.observeAsState()

    LaunchedEffect(food) {
        wineViewModel.getPairWine(food)
    }

    if (winePair != null) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp)) {
            Text(text = "Wine pair ${food}", style = MaterialTheme.typography.titleMedium)
            Text(text = winePair!!.pairingText, style = MaterialTheme.typography.bodyMedium)
            LazyColumn(modifier = Modifier.heightIn(max = 800.dp)) {
                items(winePair!!.productMatches.size) { index ->
                    val wineP = winePair!!.productMatches[index]
                    WineItem(wine = wineP)
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}