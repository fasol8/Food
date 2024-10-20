package com.sol.food.utils

import androidx.compose.ui.graphics.Color

fun colorCardType(title: String): Color {
    return when (title) {
        "Ingredients" -> Color.Green
        "Instructions" -> Color.Magenta
        else -> Color.Cyan
    }
}