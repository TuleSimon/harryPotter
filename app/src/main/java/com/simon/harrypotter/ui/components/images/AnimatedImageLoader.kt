@file:OptIn(ExperimentalAnimationApi::class)

package com.simon.harrypotter.ui.components.images

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.placeholder
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.harrypotter.ui.components.BodyText


@Composable
fun AnimatedImageLoader(character:CharactersResponseItem?,modifier: Modifier,
                        @DrawableRes errorImage:Int,loader:Boolean=false,
                        onClick:()->Unit) {


    val loading = remember {
        mutableStateOf(true)
    }

    Column(modifier = Modifier.fillMaxWidth().clickable {
        onClick()
    },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        AsyncImage(
            modifier = modifier.placeholder(
                if (!loader) loading.value else true,
                color = colorScheme.onBackground.copy(0.1f),
                shape = CircleShape, highlight = PlaceholderHighlight.fade()
            ),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(LocalContext.current)
                .data(character?.image)
                .build(),
            onError = {

                loading.value = false
            },
            onSuccess = {

                loading.value = false
            },
            onLoading = {
                loading.value = true
            },
            error = painterResource(id = errorImage),
            contentDescription = null
        )

        BodyText(text = character?.name?:"Loading",
            modifier = Modifier.placeholder(
                if (!loader) loading.value else true,
                color = colorScheme.onBackground.copy(0.1f),
                shape = shapes.small, highlight = PlaceholderHighlight.fade()
            ), textAlign = TextAlign.Center,
            maxlines = 2)


    }

}


private enum class State { PLACING, PLACED }

data class ScaleAndAlphaArgs(
    val fromScale: Float,
    val toScale: Float,
    val fromAlpha: Float,
    val toAlpha: Float
)

@Composable
fun scaleAndAlpha(
    args: ScaleAndAlphaArgs,
    animation: FiniteAnimationSpec<Float>
): Pair<Float, Float> {
    val transitionState = remember { MutableTransitionState(State.PLACING).apply { targetState = State.PLACED } }
    val transition = updateTransition(transitionState, label = "")
    val alpha by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
        when (state) {
            State.PLACING -> args.fromAlpha
            State.PLACED -> args.toAlpha
        }
    }
    val scale by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
        when (state) {
            State.PLACING -> args.fromScale
            State.PLACED -> args.toScale
        }
    }
    return alpha to scale
}


@Composable
fun LazyGridState.calculateDelayAndEasing(index: Int, columnCount: Int): Pair<Int, Easing> {
    val row = index / columnCount
    val column = index % columnCount
    val firstVisibleRow = firstVisibleItemIndex
    val visibleRows = layoutInfo.visibleItemsInfo.count()
    val scrollingToBottom = firstVisibleRow < row
    val isFirstLoad = visibleRows == 0
    val rowDelay = 200 * when {
        isFirstLoad -> row // initial load
        scrollingToBottom -> visibleRows + firstVisibleRow - row // scrolling to bottom
        else -> 1 // scrolling to top
    }
    val scrollDirectionMultiplier = if (scrollingToBottom || isFirstLoad) 1 else -1
    val columnDelay = column * 150 * scrollDirectionMultiplier
    val easing = if (scrollingToBottom || isFirstLoad) LinearOutSlowInEasing else FastOutSlowInEasing
    return rowDelay + columnDelay to easing
}

@Composable
fun LazyListState.calculateDelayAndEasing(index: Int, columnCount: Int): Pair<Int, Easing> {
    val row = index / columnCount
    val column = index % columnCount
    val firstVisibleRow = firstVisibleItemIndex
    val visibleRows = layoutInfo.visibleItemsInfo.count()
    val scrollingToBottom = firstVisibleRow < row
    val isFirstLoad = visibleRows == 0
    val rowDelay = 200 * when {
        isFirstLoad -> row // initial load
        scrollingToBottom -> visibleRows + firstVisibleRow - row // scrolling to bottom
        else -> 1 // scrolling to top
    }
    val scrollDirectionMultiplier = if (scrollingToBottom || isFirstLoad) 1 else -1
    val columnDelay = column * 150 * scrollDirectionMultiplier
    val easing = if (scrollingToBottom || isFirstLoad) LinearOutSlowInEasing else FastOutSlowInEasing
    return rowDelay + columnDelay to easing
}
