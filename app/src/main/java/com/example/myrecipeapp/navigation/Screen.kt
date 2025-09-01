package com.example.myrecipeapp.navigation

sealed class Screen(val route : String) {

    object RecipeScreen: Screen(route = "recipescreen")
    object DetailScreen: Screen(route = "detailscreen")

    object DishesScreen: Screen(route = "dishes_screen/{categoryName}") {
        fun createRoute(categoryName: String) = "dishes_screen/$categoryName"
    }

}