package com.example.myrecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myrecipeapp.ui.category_detail.CategoryDetailScreen
import com.example.myrecipeapp.ui.category_list.CategoryViewModel
import com.example.myrecipeapp.ui.category_list.CategoryListScreen
import com.example.myrecipeapp.data.model.Category
import com.example.myrecipeapp.ui.dishes_list.DishesListScreen
import com.example.myrecipeapp.ui.dishes_list.DishesViewModel

@Composable
fun AppNavigation(navController: NavHostController) {

    val recipeViewModel: CategoryViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState

    NavHost(navController = navController,startDestination = Screen.RecipeScreen.route){
        composable(route = Screen.RecipeScreen.route) {
            CategoryListScreen(
                viewState = viewState,
                navigateToDetail = {
                    //This part is responsible for passing data from the current scrren to the detail screen.
                    // it utilizes the savedStateHandle, which is a component of the Compose Navigation framework.
                    navController.currentBackStackEntry?.savedStateHandle?.set("cat", it)
                    navController.navigate(Screen.DetailScreen.route)

                }
            )
        }

        composable(route = Screen.DetailScreen.route){
            val category = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Category>("cat") ?: Category("", "", "", "")

            CategoryDetailScreen(
                category = category,
                onShowDishesClicked = { categoryName ->
                    navController.navigate(Screen.DishesScreen.createRoute(categoryName))
                }
            )
        }

        composable(
            route = Screen.DishesScreen.route,
            arguments = listOf(navArgument("categoryName") {type = NavType.StringType})
        ) {  backStackEntry ->

            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            val viewModel: DishesViewModel = viewModel()
            val viewState by viewModel.mealState

            LaunchedEffect(key1 = categoryName) {
                viewModel.fetchMeals(categoryName)
            }

            DishesListScreen(
                viewState = viewState,
                navigateToRecipe = { mealId ->
                    println("Navigate to recipe with ID: $mealId")
                }
            )

        }

    }
}
