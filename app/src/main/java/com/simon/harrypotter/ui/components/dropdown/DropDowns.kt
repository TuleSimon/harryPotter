package com.simon.harrypotter.ui.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.harrypotter.ui.components.buttons.FilledButton
import com.simon.harrypotter.ui.screens.homescreen.components.CharactersRowItem
import com.simon.harrypotter.ui.theme.defaultPadding


@Composable
fun SearchDropDowns(
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    modifier: Modifier,
    items: List<CharactersResponseItem>,
    hasMoreItems: Boolean,
    onClick: (Int) -> Unit,
    onSeeAll:()->Unit,
) {
    MaterialTheme(
        shapes = shapes.copy(extraSmall = RoundedCornerShape(15.dp))
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { if (expanded) setExpanded(false) },
            modifier = modifier.then(
                Modifier
                    .border(
                        width = 1.dp,
                        color = if (isSystemInDarkTheme()) colorScheme.outline
                        else colorScheme.primary.copy(0.2f),
                        shape = shapes.medium
                    )
                    .background(colorScheme.background)
                    .padding(defaultPadding)
                    .clipToBounds()
            ),
            properties = PopupProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                clippingEnabled = true
            )
        ) {
            items.forEachIndexed { index, character ->
                DropdownMenuItem(contentPadding = PaddingValues(0.dp), onClick = {
                    onClick(index)
                    setExpanded(false)
                }) {

                    CharactersRowItem(modifier = Modifier.fillMaxWidth(), character =character )
                }
            }

            if(hasMoreItems){

                FilledButton(modifier= Modifier.padding(10.dp).fillMaxWidth(), text = "See all") {
                    onSeeAll()
                }

            }
        }
    }
}
