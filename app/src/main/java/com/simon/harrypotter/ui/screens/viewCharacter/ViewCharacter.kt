package com.simon.harrypotter.ui.screens.viewCharacter

import androidx.compose.animation.Crossfade
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.simon.data.data.network.repositories.NetworkResult
import com.simon.harrypotter.R
import com.simon.harrypotter.core.viewModels.AppViewModel
import com.simon.harrypotter.core.viewModels.Events
import com.simon.harrypotter.ui.components.appbars.SimonAppBars
import com.simon.harrypotter.ui.components.buttons.SimonIconButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCharacter(characterId:Int,appViewModel:AppViewModel = koinViewModel()){

    val characters = appViewModel.characters.collectAsState(initial = NetworkResult.Idle)
    val character = remember(characters.value){
        (characters.value as NetworkResult.Success).data
    }

    Scaffold(
        topBar = {
            SimonAppBars(title = stringResource(id = R.string.welcome),
                shouldShowBackButton = false,
                actions = {
                    SimonIconButton(icon = Icons.Outlined.Search) {
                        appViewModel.performEvent(Events.Search(""))
                    }
                })
        }
    ){

    }

}