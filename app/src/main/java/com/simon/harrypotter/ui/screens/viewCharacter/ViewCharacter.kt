package com.simon.harrypotter.ui.screens.viewCharacter

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.simon.data.data.network.repositories.NetworkResult
import com.simon.data.models.characters.isMale
import com.simon.harrypotter.R
import com.simon.harrypotter.core.viewModels.AppViewModel
import com.simon.harrypotter.core.viewModels.Events
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.TitleTextMedium
import com.simon.harrypotter.ui.components.appbars.SimonAppBars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCharacter(characterId:String,appViewModel:AppViewModel ) {

    val characters = appViewModel.characters.collectAsState(initial = NetworkResult.Idle)
    val character = remember(characters.value) {
        if (characters.value is NetworkResult.Success) {
            (characters.value as NetworkResult.Success).data.find { it.id == characterId }
        } else {
            null
        }
    }

    val systemController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()
    DisposableEffect( true ){
        systemController.setStatusBarColor(Color.Transparent,false)
        onDispose {
            systemController.setStatusBarColor(Color.Transparent,!darkTheme)
        }
    }

    Box() {
        
        AsyncImage(model =R.drawable.cover , contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .align(Alignment.TopCenter)
            .alpha(0.8f), contentScale = ContentScale.Crop)
        
        Scaffold(
            topBar = {
                SimonAppBars(
                    title = character?.name ?: "",
                    shouldShowBackButton = true,
                    appBarColor = Color.White,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                ) {
                    appViewModel.performEvent(Events.NavigateUp)
                }
            },
            containerColor = Color.Transparent
        ) {

            Column(
                Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                AsyncImage(
                    character?.image,
                    modifier = Modifier
                        .fillMaxWidth(0.35f)
                        .aspectRatio(1f)
                        .border(3.dp, colorScheme.onPrimary, CircleShape)
                        .padding(5.dp)
                        .background(color = colorScheme.background, shape = CircleShape)
                        .clip(CircleShape),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(
                        id = if (character?.gender?.isMale() == true) R.drawable.icon_boy
                        else R.drawable.icon_girl
                    )
                )


                TitleTextMedium(text = character?.name ?: "", textAlign = TextAlign.Center,
                color = Color.White)

                BodyText(text = character?.actor ?: "Unknown", textAlign = TextAlign.Center,
                color = Color.White)


            }

        }
    }
}