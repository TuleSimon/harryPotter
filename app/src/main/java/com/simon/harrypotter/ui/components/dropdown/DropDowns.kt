package com.simon.harrypotter.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties


@Composable
fun TextScudDropDowns(
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    modifier: Modifier,
    items: List<String>,
    hasMoreItems: Boolean,
    onClick: (Int) -> Unit,
    selectedIndex: Int = 0
) {
    MaterialTheme(
        shapes = shapes.copy(extraSmall = RoundedCornerShape(10.dp))
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { if (expanded) setExpanded(false) },
            modifier = modifier.then(
                Modifier
                    .border(
                        width = 1.dp,
                        color = if (isSystemInDarkTheme())  colorScheme.outline
                            else colorScheme.primary.copy(0.2f),
                        shape = shapes.medium
                    )
                    .background(colorScheme.surface)
                    .clipToBounds()
            ),
            properties = PopupProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                clippingEnabled = true
            )
        ) {
            items.forEachIndexed { index, label ->
                DropdownMenuItem(contentPadding = PaddingValues(0.dp), onClick = {
                    onClick(index)
                    if (expanded)
                        setExpanded(false)
                }) {


                }
            }
        }
    }
}
