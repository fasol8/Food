package com.sol.food.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.sol.food.ui.theme.darkOliveGreen
import com.sol.food.ui.theme.lightOliveGreen
import com.sol.food.ui.theme.mossGreen
import com.sol.food.ui.theme.oliveDrab
import com.sol.food.ui.theme.paleOliveGreen
import com.sol.food.ui.theme.sage
import com.sol.food.ui.theme.yellowGreen

@Composable
fun ExpandableCard(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorCardType(title),
            contentColor = Color.White,
            disabledContainerColor = colorCardType(title),
            disabledContentColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = ""
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                content()
            }
        }
    }
}

@Composable
fun ExpandableItemCard(
    title: String,
    icon: Painter,
    content: @Composable () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { isExpanded = !isExpanded } // Cambia el estado cuando se hace clic en la tarjeta
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            // Ícono y Título
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = if (isExpanded) MaterialTheme.colorScheme.primary else Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )

            AnimatedVisibility(visible = isExpanded) {
                content()
            }
        }
    }
}

private fun colorCardType(title: String): Color {
    return when (title) {
        "Monday" -> darkOliveGreen
        "Tuesday" -> oliveDrab
        "Wednesday" -> sage
        "Thursday" -> yellowGreen
        "Friday" -> mossGreen
        "Saturday" -> lightOliveGreen
        "Sunday" -> paleOliveGreen
        "Ingredients" -> oliveDrab
        "Instructions" -> yellowGreen
        "Nutritional Information" -> lightOliveGreen
        "Nutrients" -> lightOliveGreen
        "Properties" -> paleOliveGreen
        else -> darkOliveGreen
    }
}