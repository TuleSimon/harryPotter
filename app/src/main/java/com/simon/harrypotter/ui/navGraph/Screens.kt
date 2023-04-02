package com.simon.harrypotter.ui.navGraph

sealed class Screen(val route:String, vararg val args:String){
    object RootScreen:Screen("root")
    object HomeScreen:Screen("home")

    object ViewCharacterScreen:Screen("view_character", VIEW_CHARACTER_ID_ARGS)
}

const val VIEW_CHARACTER_ID_ARGS = "character_id"