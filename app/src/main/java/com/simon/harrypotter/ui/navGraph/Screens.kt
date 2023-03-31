package com.simon.harrypotter.ui.navGraph

sealed class Screen(val route:String){
    object RootScreen:Screen("root")
    object HomeScreen:Screen("home")
}