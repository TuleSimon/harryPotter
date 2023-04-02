package com.simon.harrypotter.ui.screens.viewCharacter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.simon.data.data.network.repositories.NetworkResult
import com.simon.data.models.characters.isMale
import com.simon.harrypotter.R
import com.simon.harrypotter.core.viewModels.AppViewModel
import com.simon.harrypotter.core.viewModels.Events
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.TitleTextMedium
import com.simon.harrypotter.ui.components.appbars.SimonAppBars
import com.simon.harrypotter.ui.components.buttons.SimonIconButton

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

    Scaffold(
        topBar = {
            SimonAppBars(title = character?.name ?: "",
                shouldShowBackButton = true,
               ){
                appViewModel.performEvent(Events.NavigateUp)
            }
        }
    ) {

        Column(
            Modifier.padding(top = it.calculateTopPadding()).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            AsyncImage(
                character?.image,
                modifier = Modifier
                    .fillMaxWidth(0.35f)
                    .aspectRatio(1f)
                    .border(3.dp, colorScheme.onBackground, CircleShape)
                    .padding(5.dp)
                    .background(color = colorScheme.onBackground, shape = CircleShape)
                    .clip(CircleShape),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(
                    id = if (character?.gender?.isMale() == true) R.drawable.icon_boy
                    else R.drawable.icon_girl
                )
            )


            TitleTextMedium(text = character?.name ?: "", textAlign = TextAlign.Center)

            BodyText(text = character?.actor ?: "Unknown", textAlign = TextAlign.Center)


        }

    }
}