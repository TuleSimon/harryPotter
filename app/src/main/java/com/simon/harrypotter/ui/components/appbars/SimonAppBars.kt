package com.simon.harrypotter.ui.components.appbars

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.simon.harrypotter.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimonAppBars(
    modifier: Modifier = Modifier,
    shouldShowBackButton:Boolean = true,
    title: String,
    @DrawableRes navigationIcon: Int = R.drawable.icon_back,
    actions: @Composable() (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.background),
    onClick: () -> Unit = { },
) {

    CenterAlignedTopAppBar(
        colors = colors,
        navigationIcon = {
            if(shouldShowBackButton) {
                IconButton(onClick = { onClick.invoke() }) {
                    Icon(
                        modifier = Modifier
                            .width(20.dp),
                        painter = painterResource(id = navigationIcon),
                        contentDescription = title
                    )
                }
            }
        },
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 25.dp).fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
                    .copy(color = MaterialTheme.colorScheme.onBackground)
            )
        },

        actions = actions,
        modifier = modifier

    )
}