package com.simon.harrypotter.ui.components.buttons

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SimonIconButton(icon:ImageVector,
                    tint: Color =MaterialTheme.colorScheme.onBackground.copy(0.7f),
                            onClick: () -> Unit ){
    IconButton(onClick = {onClick.invoke()}) {
        Image(
            imageVector = icon,
            contentDescription = null,
            colorFilter = ColorFilter.tint(tint)
        )
    }
}