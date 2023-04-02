package com.simon.harrypotter.ui.screens.homescreen.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.harrypotter.R
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.TitleText
import com.simon.harrypotter.ui.components.buttons.FilledButton
import com.simon.harrypotter.ui.components.images.ScaleAndAlphaArgs
import com.simon.harrypotter.ui.components.images.calculateDelayAndEasing
import com.simon.harrypotter.ui.components.images.scaleAndAlpha
import onlyBottomRounded

@Composable
fun SearchLazyColumn(modifier: Modifier=Modifier,
                     items: List<CharactersResponseItem>) {
    val state = rememberLazyListState()

    LazyColumn(
    modifier = modifier
        .fillMaxWidth()
        .border(
            1.dp,
            color = colorScheme.primary, shape =onlyBottomRounded)
        .background(shape=onlyBottomRounded, color = Color.Unspecified)
        .clip(onlyBottomRounded)
        .clipToBounds()
        ,state = state,
    ) {

        if (items.isEmpty()) {



        } else {
            itemsIndexed(items) { index, item ->
                val (delay, easing) = state.calculateDelayAndEasing(index, 1)
                val animation =
                    tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
                val args =
                    ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
                val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

                CharactersRowItem(
                    modifier = Modifier.fillMaxWidth()
                        .graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                    character = item
                )
            }
        }
    }
}


@Composable
private fun EmptyLayout(){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AsyncImage(
            model = R.drawable.icon_error,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(20.dp))

        TitleText(text = "AN ERROR OCCURRED")
        BodyText(
            text = "we couldn't fetch characters right, " +
                    "now please try again later",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.75f)
        )



    }

}