@file:OptIn(ExperimentalMaterial3Api::class)

package com.simon.harrypotter.ui.screens.homescreen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import com.simon.harrypotter.R
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.appbars.SimonAppBars
import com.simon.harrypotter.ui.components.buttons.SimonIconButton
import com.simon.harrypotter.ui.theme.defaultPadding

@Composable
fun HomeScreen(){

    val isSearchMode  = remember{
        mutableStateOf(false)
    }

    val searchValue = rememberSaveable(){
        mutableStateOf("")
    }

    BackHandler(isSearchMode.value) {
        isSearchMode.value=false
    }

    Scaffold(
        topBar = {
            Crossfade(targetState = isSearchMode.value) { value ->
                if(!value) {
                    SimonAppBars(title = stringResource(id = R.string.welcome),
                        shouldShowBackButton = false,
                        actions = {
                            SimonIconButton(icon = Icons.Outlined.Search) {
                                isSearchMode.value=true
                            }
                        })
                }
                else{
                    OutlinedTextField(value = searchValue.value,
                        onValueChange ={newText -> searchValue.value=newText },
                        textStyle = typography.bodyMedium.copy(
                            color = colorScheme.onBackground.copy(0.7f)),
                        placeholder ={ BodyText(text = "Search") },
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(defaultPadding)
                            .fillMaxWidth(),
                        shape = shapes.large,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = colorScheme.primary.copy(0.1f)
                        ),
                        leadingIcon = {
                            Image(imageVector = Icons.Outlined.Search,
                                contentDescription = null, colorFilter = ColorFilter.tint(
                                    colorScheme.primary))
                        },
                        trailingIcon = {
                            if(searchValue.value.isNotEmpty()) {
                                SimonIconButton(icon = Icons.Filled.Cancel,
                                    tint = colorScheme.primary) {
                                    searchValue.value = ""
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {

        LazyColumn(modifier = Modifier.padding(top=it.calculateTopPadding()) ){

        }

    }

}