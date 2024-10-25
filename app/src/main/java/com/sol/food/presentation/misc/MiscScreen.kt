package com.sol.food.presentation.misc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sol.food.domain.model.misc.MiscType

@Composable
fun MiscScreen(miscType: MiscType, miscViewModel: MiscViewModel = hiltViewModel()) {
    val joke by miscViewModel.randomMisc.observeAsState()

    LaunchedEffect(miscType) {
        miscViewModel.getRandomMisc(miscType)
    }

    if (joke != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = if (miscType.typeName == "Joke") "Joke Random" else "Trivia Random",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Gray
                    )
                    Text(text = joke!!.text, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}