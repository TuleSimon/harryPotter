package com.simon.harrypotter.ui.screens.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.simon.data.models.characters.CharactersResponseItem
import com.simon.data.models.characters.isMale
import com.simon.harrypotter.R
import com.simon.harrypotter.ui.components.BodyText
import com.simon.harrypotter.ui.components.BodyTextBold
import com.simon.harrypotter.ui.components.images.AnimatedImageLoader
import com.simon.harrypotter.ui.theme.defaultPadding
import shadowMedium
import shadowSmall

@Composable
fun CharactersRowItem(modifier : Modifier, character:CharactersResponseItem,
        onClick:()->Unit){


        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .shadowMedium(shapes = RoundedCornerShape(0.dp))
                .background(color = colorScheme.surface, shape = RoundedCornerShape(0.dp))
                .padding(defaultPadding)
                .clickable {
                    onClick()
                }, horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            AsyncImage(
                 character.image,
                modifier = Modifier
                    .fillMaxWidth(0.15f)
                    .aspectRatio(1f)
                    .clip(CircleShape),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = if (character.gender.isMale()) R.drawable.icon_boy
                else R.drawable.icon_girl)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                BodyTextBold(text = character.name)
                BodyText(text = character.house)

            }

        }


}