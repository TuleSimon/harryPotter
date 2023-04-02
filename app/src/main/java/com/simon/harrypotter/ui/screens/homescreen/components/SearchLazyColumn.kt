package com.simon.harrypotter.ui.screens.homescreen.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.harrypotter.ui.components.images.ScaleAndAlphaArgs
import com.simon.harrypotter.ui.components.images.calculateDelayAndEasing
import com.simon.harrypotter.ui.components.images.scaleAndAlpha

@Composable
fun SearchLazyColumn(items: List<CharactersResponseItem>) {
    val state = rememberLazyListState()
    LazyColumn(
    modifier = Modifier
        .fillMaxWidth()
        .border(
            1.dp,
            color = colorScheme.primary, shape = shapes.large
                .copy(topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp))
        ),state = state,
    ) {


        itemsIndexed(items) {index,item ->
            val (delay, easing) = state.calculateDelayAndEasing(index, 1)
            val animation = tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
            val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
            val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

            CharactersRowItem(
                modifier = Modifier.fillMaxWidth()
                    .graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                character = item
            )
        }
    }
}