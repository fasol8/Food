@file:OptIn(ExperimentalMaterial3Api::class)

package com.sol.food.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    var active by remember { mutableStateOf(false) }

    val colors1 = SearchBarDefaults.colors()
    androidx.compose.material3.SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = { onQueryChange(it) },
                onSearch = {
                    onSearch(query)
                    active = false
                },
                expanded = active,
                onExpandedChange = { active = it },
                enabled = true,
                placeholder = { Text(placeholder) },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = {
                            onQueryChange("")
                            active = false
                            onSearch("")
                        }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                colors = colors1.inputFieldColors,
                interactionSource = null,
            )
        },
        expanded = active,
        onExpandedChange = { active = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = SearchBarDefaults.inputFieldShape,
        colors = colors1,
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
        content = {},
    )
}
