package com.simon.harrypotter.ui.navGraph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.simon.harrypotter.ui.components.BodyTextBold

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetUpNavGraph(navController: NavHostController) {

    AnimatedNavHost(
        navController = navController,
        startDestination =Screen.HomeScreen.route
    ) {

        composable(route= Screen.HomeScreen.route){
            BodyTextBold("Hello", modifier = Modifier.statusBarsPadding())
        }

    }
}