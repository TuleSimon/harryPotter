package com.simon.harrypotter.ui.screens.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.models.characters.isMale
import com.simon.harrypotter.R
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.images.AnimatedImageLoader
import com.simon.harrypotter.ui.theme.defaultPadding
import shadowMedium
import shadowSmall

@Composable
fun CharactersRowItem(modifier : Modifier, character:CharactersResponseItem){
    
    Row(verticalAlignment = Alignment.CenterVertically,
    modifier = modifier.shadowMedium().
    background(color = colorScheme.surface, shape = shapes.medium)
        .clip(shapes.medium)
        .padding(defaultPadding).clickable {

    }, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        
        AnimatedImageLoader(image =character.image , modifier = Modifier
            .fillMaxWidth(0.3f)
            .aspectRatio(1f) , errorImage = if(character.gender.isMale()) R.drawable.icon_boy
        else R.drawable.icon_girl)

        Column(modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center) {

            BodyText(text = character.name)
            BodyText(text = character.house)

        }
        
    }
    
}