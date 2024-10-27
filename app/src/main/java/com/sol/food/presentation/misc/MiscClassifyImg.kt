package com.sol.food.presentation.misc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.sol.food.domain.model.misc.UrlFoodImages

@Composable
fun MiscClassifyImg(miscViewModel: MiscViewModel = hiltViewModel()) {
    val imgClassify by miscViewModel.classifyImage.observeAsState()
    var urlText by remember { mutableStateOf("") }
    var isValidUrl by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Classify Image", style = MaterialTheme.typography.titleLarge)
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
                        miscViewModel.getClassifyImage(urlText)
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
        Text(text = "Examples", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(30.dp))
        LazyRow {
            items(UrlFoodImages.entries.size) { index ->
                val image = UrlFoodImages.entries[index].url
                ImgClassifyItem(image, miscViewModel)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        if (imgClassify != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(text = "Result", style = MaterialTheme.typography.titleMedium)
                Card(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        AsyncImage(
                            model = urlText,
                            contentDescription = "image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = imgClassify!!.category,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${imgClassify!!.probability}%",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ImgClassifyItem(image: String, miscViewModel: MiscViewModel) {
    AsyncImage(
        model = image,
        contentDescription = "image",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .height(140.dp)
            .width(350.dp)
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable { miscViewModel.getClassifyImage(image) })
}
