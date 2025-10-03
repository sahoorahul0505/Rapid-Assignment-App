package com.rkscoding.rapidassignment.ui.common_components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rkscoding.rapidassignment.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    active: Boolean = false,
    placeHolder: String,
) {
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {},
                expanded = active,
                onExpandedChange = { },
                placeholder = {
                    Text(
                        text = placeHolder
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(270f)
                    )
                },
                trailingIcon = {

                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedPlaceholderColor = Color.White.copy(.8f),
                    focusedPlaceholderColor = Color.White.copy(.7f),
                    cursorColor = Color.White.copy(.9f)
                )
            )
        },
        expanded = active,
        onExpandedChange = { },
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color.Black.copy(alpha = .2f)
        ),
        tonalElevation = SearchBarDefaults.TonalElevation,
        shadowElevation = SearchBarDefaults.ShadowElevation,
        windowInsets = SearchBarDefaults.windowInsets,
    ) {

    }

}