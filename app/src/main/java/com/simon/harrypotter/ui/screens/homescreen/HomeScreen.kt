@file:OptIn(ExperimentalMaterial3Api::class)

package com.simon.harrypotter.ui.screens.homescreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.models.characters.isMale
import com.simon.data.network.repositories.NetworkResult
import com.simon.harrypotter.R
import com.simon.harrypotter.core.viewModels.AppViewModel
import com.simon.harrypotter.core.viewModels.Events
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.TitleText
import com.simon.harrypotter.ui.components.appbars.SimonAppBars
import com.simon.harrypotter.ui.components.buttons.FilledButton
import com.simon.harrypotter.ui.components.buttons.SimonIconButton
import com.simon.harrypotter.ui.components.dropdown.SearchDropDowns
import com.simon.harrypotter.ui.components.images.AnimatedImageLoader
import com.simon.harrypotter.ui.components.images.ScaleAndAlphaArgs
import com.simon.harrypotter.ui.components.images.calculateDelayAndEasing
import com.simon.harrypotter.ui.components.images.scaleAndAlpha
import com.simon.harrypotter.ui.theme.defaultPadding
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun HomeScreen(appViewModel: AppViewModel = koinViewModel()){

    //UI STATE
    val eventsState = appViewModel.uiEvents.collectAsState(initial = Events.Idle)

    //SEARCH
    val isSearchMode  = remember(eventsState.value){
        eventsState.value is Events.Search
    }
    val searchValue =remember(eventsState.value){
        if(eventsState.value is Events.Search){
            (eventsState.value as Events.Search).searchText
        }
        else{
            ""
        }
    }
    var searchFieldSize by remember { mutableStateOf(Size.Zero) }
    val shouldShowDropDown = appViewModel.showDropDown.collectAsState(initial = false)


    //BACK HANDLER
    BackHandler(isSearchMode) {
        appViewModel.performEvent(Events.Idle)
    }

    //API
    fun getCharacters(){
        appViewModel.getAllCharacters()
    }

    val characters = appViewModel.characters.collectAsState(initial = NetworkResult.Idle)
    val searchedCharacters = appViewModel.searchedCharacters.collectAsState(initial = emptyList())
    val hasMoreItems = remember(searchedCharacters.value){
        searchedCharacters.value.size>5
    }
    LaunchedEffect(true){
        getCharacters()
    }
    val isLoading = remember(characters.value){
        Timber.v(characters.value.toString())
        characters.value is NetworkResult.Loading
    }


    //UI STATES
    val state = rememberLazyGridState()

    Scaffold(
        topBar = {
            Crossfade(targetState = isSearchMode) { value ->
                if(!value) {
                    SimonAppBars(title = stringResource(id = R.string.welcome),
                        shouldShowBackButton = false,
                        actions = {
                            SimonIconButton(icon = Icons.Outlined.Search) {
                                appViewModel.performEvent(Events.Search(""))
                            }
                        })
                }
                else{
                    Column(Modifier.statusBarsPadding()
                        .padding(defaultPadding),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                        OutlinedTextField(value = searchValue,
                            onValueChange = { newText ->
                                appViewModel.performEvent(
                                    Events.Search(
                                        newText
                                    )
                                )
                            },
                            textStyle = typography.bodyMedium.copy(
                                color = colorScheme.onBackground.copy(0.7f)
                            ),
                            placeholder = { BodyText(text = "Search") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    searchFieldSize = coordinates.size.toSize()
                                },
                            shape = shapes.large,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = colorScheme.surface,
                                unfocusedBorderColor = colorScheme.outline
                            ),
                            leadingIcon = {
                                Image(
                                    imageVector = Icons.Outlined.Search,
                                    contentDescription = null, colorFilter = ColorFilter.tint(
                                        colorScheme.primary
                                    )
                                )
                            },
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = searchValue.isNotEmpty(),
                                    enter = fadeIn(
                                        tween(
                                            500,
                                            easing = LinearEasing
                                        )
                                    ) + slideInHorizontally(),
                                    exit = fadeOut()
                                ) {

                                    SimonIconButton(
                                        icon = Icons.Filled.Cancel,
                                        tint = colorScheme.primary
                                    ) {
                                        appViewModel.performEvent(Events.Search(""))
                                    }
                                }
                            }
                        )

                        SearchDropDowns(
                            expanded = shouldShowDropDown.value,
                            setExpanded = {},
                            modifier = Modifier.width(with(LocalDensity.current) { searchFieldSize.width.toDp() }),
                            items = if (hasMoreItems) searchedCharacters.value.subList(
                                0,
                                4
                            ) else searchedCharacters.value,
                            hasMoreItems = hasMoreItems,
                            onClick = { }
                        ) {

                        }
                    }



                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {

        LazyVerticalGrid(columns =GridCells.Fixed(4),
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .padding(defaultPadding),
            state = state,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            if(isLoading) {
                items(10) {
                    AnimatedImageLoader(
                        "",
                        loader=true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .shadow(
                                3.dp, CircleShape, true,
                                colorScheme.onBackground.copy(0.1f),
                                colorScheme.onBackground.copy(0.05f)
                            ),
                        errorImage = R.drawable.icon_boy
                    )
                }
            }
            else  if (characters.value is NetworkResult.Success) {
                itemsIndexed((characters.value as NetworkResult.Success<List<CharactersResponseItem>>).data,
                key={ _,item -> item.id}) {
                        index, character ->

                    val (delay, easing) = state.calculateDelayAndEasing(index, 4)
                    val animation = tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
                    val args = ScaleAndAlphaArgs(fromScale = 2f, toScale = 1f, fromAlpha = 0f, toAlpha = 1f)
                    val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

                    AnimatedImageLoader(
                        character.image,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .shadow(
                                3.dp, CircleShape, true,
                                colorScheme.onBackground.copy(0.1f),
                                colorScheme.onBackground.copy(0.05f)
                            )
                            .graphicsLayer(alpha = alpha, scaleX = scale, scaleY = scale),
                        errorImage = if(character.gender.isMale()) R.drawable.icon_boy
                        else R.drawable.icon_girl
                    )
                }
            }
            else if(characters.value is NetworkResult.Failure){

                item(span = { GridItemSpan(4) }){
                    Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        AsyncImage(model = R.drawable.icon_error,
                            contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth)

                        Spacer(modifier = Modifier.height(20.dp))

                        TitleText(text = "AN ERROR OCCURRED")
                        BodyText(text = "we couldn't fetch characters right, " +
                                "now please try again later",
                        textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(0.75f))

                        Spacer(modifier = Modifier.height(20.dp))
                        FilledButton(modifier = Modifier.fillMaxWidth(0.75F), text = "Retry") {
                            getCharacters()
                        }

                    }
                }
            }
        }

    }

}