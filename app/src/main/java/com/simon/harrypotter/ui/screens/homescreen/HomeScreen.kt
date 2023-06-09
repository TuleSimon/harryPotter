@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.simon.harrypotter.ui.screens.homescreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.models.characters.isMale
import com.simon.data.data.network.repositories.NetworkResult
import com.simon.harrypotter.R
import com.simon.harrypotter.core.viewModels.AppViewModel
import com.simon.harrypotter.core.viewModels.Events
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.TitleText
import com.simon.harrypotter.ui.components.appbars.SimonAppBars
import com.simon.harrypotter.ui.components.buttons.FilledButton
import com.simon.harrypotter.ui.components.buttons.SimonIconButton
import com.simon.harrypotter.ui.components.images.AnimatedImageLoader
import com.simon.harrypotter.ui.components.images.ScaleAndAlphaArgs
import com.simon.harrypotter.ui.components.images.calculateDelayAndEasing
import com.simon.harrypotter.ui.components.images.scaleAndAlpha
import com.simon.harrypotter.ui.navGraph.Screen
import com.simon.harrypotter.ui.screens.homescreen.components.SearchLazyColumn
import com.simon.harrypotter.ui.screens.homescreen.components.SearchTextField
import com.simon.harrypotter.ui.theme.defaultPadding
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import onlyBottomRounded
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun HomeScreen(appViewModel: AppViewModel ){

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

    LaunchedEffect(true){
        appViewModel.characters.cancellable().collect{
            if(it !is NetworkResult.Success){
                getCharacters()
            }
            return@collect
        }
    }
    val isLoading = remember(characters.value){
        Timber.v(characters.value.toString())
        characters.value is NetworkResult.Loading
    }


    //UI STATES
    val state = rememberLazyGridState()

    Scaffold(
        topBar = {
            Crossfade(targetState = isSearchMode, label = "crossFade") { value ->
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

                    Column(
                        Modifier
                            .statusBarsPadding()
                            .padding(defaultPadding)
                            .clip(
                                if (shouldShowDropDown.value) onlyBottomRounded else RoundedCornerShape(
                                    0.dp
                                )
                            ),
                    ) {

                        SearchTextField(searchValue = searchValue,
                            onValueChange = { newText ->
                                appViewModel.performEvent(
                                    Events.Search(
                                        newText
                                    )
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shouldShowDropDown = shouldShowDropDown.value,
                        ) {
                            appViewModel.performEvent(Events.Search(""))

                        }

                        if(shouldShowDropDown.value) {

                            SearchLazyColumn(items = searchedCharacters.value){character ->
                                navigateToViewCharacter(appViewModel,character.id)
                            }

                        }

                    }



                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {

        if (!shouldShowDropDown.value) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize()
                    .padding(defaultPadding),
                state = state,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                if (isLoading) {
                    items(10) {
                        AnimatedImageLoader(
                            null,
                            loader = true,
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
                        ){

                        }
                    }
                } else if (characters.value is NetworkResult.Success) {
                    itemsIndexed((characters.value as NetworkResult.Success<List<CharactersResponseItem>>).data,
                        key = { _, item -> item.id }) { index, character ->

                        val (delay, easing) = state.calculateDelayAndEasing(index, 4)
                        val animation =
                            tween<Float>(durationMillis = 500, delayMillis = delay, easing = easing)
                        val args = ScaleAndAlphaArgs(
                            fromScale = 2f,
                            toScale = 1f,
                            fromAlpha = 0f,
                            toAlpha = 1f
                        )
                        val (scale, alpha) = scaleAndAlpha(args = args, animation = animation)

                        AnimatedImageLoader(
                            character,
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
                            errorImage = if (character.gender.isMale()) R.drawable.icon_boy
                            else R.drawable.icon_girl,
                        ){
                            navigateToViewCharacter(appViewModel,character.id)
                        }
                    }
                } else if (characters.value is NetworkResult.Failure) {

                    item(span = { GridItemSpan(4) }) {
                        ErrorLayout {
                            getCharacters()
                        }
                    }
                }
            }

        }
    }

}



@Composable
fun ErrorLayout(onError:()->Unit){
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

        TitleText(text = stringResource(R.string.an_error_occurred))
        BodyText(
            text = stringResource(R.string.we_couldn_t_fetch_characters_right_now_please_try_again_later),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.75f)
        )

        Spacer(modifier = Modifier.height(20.dp))
        FilledButton(modifier = Modifier.fillMaxWidth(0.75F), text = stringResource(R.string.retry)) {
            onError()
        }

    }

}


fun navigateToViewCharacter(appViewModel: AppViewModel,id:String){
    appViewModel.performEvent(Events.NavigateToScreen(Screen.ViewCharacterScreen,id))
}