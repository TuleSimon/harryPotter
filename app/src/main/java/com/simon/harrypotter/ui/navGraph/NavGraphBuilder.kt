package com.simon.harrypotter.ui.navGraph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.simon.harrypotter.ui.screens.homescreen.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetUpNavGraph(navController: NavHostController) {

    AnimatedNavHost(
        navController = navController,
        startDestination =Screen.HomeScreen.route
    ) {

        composable(route= Screen.HomeScreen.route){
            HomeScreen()
        }

    }
}