package com.simon.harrypotter.ui.navGraph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.simon.harrypotter.core.viewModels.AppViewModel
import com.simon.harrypotter.ui.screens.homescreen.HomeScreen
import com.simon.harrypotter.ui.screens.viewCharacter.ViewCharacter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetUpNavGraph(navController: NavHostController,appViewModel: AppViewModel) {

    AnimatedNavHost(
        navController = navController,
        startDestination =Screen.HomeScreen.route
    ) {

        composable(route= Screen.HomeScreen.route){
            HomeScreen(appViewModel)
        }

        composable(route= "${Screen.ViewCharacterScreen.route}/{$VIEW_CHARACTER_ID_ARGS}",
            arguments = listOf( navArgument(Screen.ViewCharacterScreen.args[0]) {
                type = NavType.StringType
                nullable=false})){
            it.arguments?.getString(
                Screen.ViewCharacterScreen.args[0])?.let { id ->
                ViewCharacter(id,appViewModel)
            }
        }

    }
}