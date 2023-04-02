@file:OptIn(ExperimentalMaterial3Api::class)

package com.simon.harrypotter.ui.screens.homescreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.simon.harrypotter.core.viewModels.Events
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.buttons.SimonIconButton

@Composable
fun SearchTextField(searchValue:String, onValueChange:(String)->Unit,
                    shouldShowDropDown:Boolean=false,
                    onCancel:()->Unit){
    OutlinedTextField(value = searchValue,
        onValueChange = { newText ->
                onValueChange(newText
            )
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
        ),
        maxLines = 1,
        placeholder = { BodyText(text = "Search") },
        modifier = Modifier
            .fillMaxWidth(),
        shape =if(shouldShowDropDown) MaterialTheme.shapes.large.copy(bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ) else MaterialTheme.shapes.large,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.surface,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        leadingIcon = {
            Image(
                imageVector = Icons.Outlined.Search,
                contentDescription = null, colorFilter = ColorFilter.tint(
                    MaterialTheme.colorScheme.primary
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
                    tint = MaterialTheme.colorScheme.primary
                ) {
                    onCancel()
                }
            }
        }
    )

}