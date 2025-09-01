package com.example.myrecipeapp.navigation

sealed class Screen(val route : String) {

    object RecipeScreen: Screen(route = "recipescreen")
    object DetailScreen: Screen(route = "detailscreen")

}