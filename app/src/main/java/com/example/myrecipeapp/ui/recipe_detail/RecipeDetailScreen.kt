package com.example.myrecipeapp.ui.recipe_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myrecipeapp.data.model.MealDetail

@Composable
fun RecipeDetailScreen(
    viewState: RecipeDetailViewModel.MealDetailState
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text("Error Occurred: ${viewState.error}", modifier = Modifier.align(Alignment.Center))
            }

            viewState.meal != null -> {
                // If data is successfully loaded, show the recipe details
                RecipeDetailView(meal = viewState.meal)
            }
        }
    }
}

@Composable
private fun RecipeDetailView(meal: MealDetail) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Meal Image
        item {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }

        // 2. Meal Title and Info
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = meal.strMeal,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Category: ${meal.strCategory} | Area: ${meal.strArea}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                // 3. Ingredients List
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Use the helper function to get a clean list
                for (ingredient in getIngredientsList(meal)) {
                    Text(text = "• $ingredient")
                }
                Spacer(modifier = Modifier.height(16.dp))

                // 4. Instructions
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = meal.strInstructions,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

// Helper function to parse the 20 ingredient/measure fields into a single, clean list
private fun getIngredientsList(meal: MealDetail): List<String> {
    val ingredients = mutableListOf<String>()
    val allIngredients = listOf(
        meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4,
        meal.strIngredient5, meal.strIngredient6, meal.strIngredient7, meal.strIngredient8,
        meal.strIngredient9, meal.strIngredient10, meal.strIngredient11, meal.strIngredient12,
        meal.strIngredient13, meal.strIngredient14, meal.strIngredient15, meal.strIngredient16,
        meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20
    )
    val allMeasures = listOf(
        meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4,
        meal.strMeasure5, meal.strMeasure6, meal.strMeasure7, meal.strMeasure8,
        meal.strMeasure9, meal.strMeasure10, meal.strMeasure11, meal.strMeasure12,
        meal.strMeasure13, meal.strMeasure14, meal.strMeasure15, meal.strMeasure16,
        meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20
    )

    for (i in allIngredients.indices) {
        val ingredient = allIngredients[i]
        val measure = allMeasures[i]
        if (!ingredient.isNullOrBlank()) {
            ingredients.add("${measure?.trim()} ${ingredient.trim()}".trim())
        }
    }
    return ingredients
}