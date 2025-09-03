package com.example.myrecipeapp.ui.dishes_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myrecipeapp.data.model.Meal

@Composable
fun DishesListScreen(
    modifier: Modifier = Modifier,
    viewState: DishesViewModel.MealState,
    navigateToRecipe: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text("Error Occurred: ${viewState.error}")
            }

            else -> {
                // Display the grid of dishes
                DishesGrid(meals = viewState.list, navigateToRecipe)
            }
        }
    }
}

@Composable
fun DishesGrid(
    meals: List<Meal>,
    navigateToRecipe: (String) -> Unit
) {
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
        items(meals) { meal ->
            DishItem(meal = meal, navigateToRecipe)
        }
    }
}

@Composable
fun DishItem(
    meal: Meal,
    navigateToRecipe: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navigateToRecipe(meal.idMeal) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = meal.strMealThumb,
            contentDescription = "Image of ${meal.strMeal}",
            modifier = Modifier.size(120.dp),
        )

        Text(
            text = meal.strMeal,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}