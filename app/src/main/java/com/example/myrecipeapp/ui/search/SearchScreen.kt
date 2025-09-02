package com.example.myrecipeapp.ui.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myrecipeapp.ui.dishes_list.DishItem
import com.example.myrecipeapp.data.model.Meal

@Composable
fun SearchScreen(
    viewState: SearchViewModel.SearchState,
    onQueryChange: (String) -> Unit,
    onSearchClicked: () -> Unit,
    navigateToRecipe: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = viewState.query,
            onValueChange = onQueryChange,
            label = { Text("Search for a meal...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onSearchClicked,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Results Section
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                viewState.error != null -> {
                    Text(text = viewState.error, modifier = Modifier.align(Alignment.Center))
                }
                else -> {

                    if(viewState.searchPerformed && viewState.results.isEmpty()) {
                        Text(
                            text = "No recipes found for \"${viewState.query}\"",
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    }

                    else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // We convert MealDetail to the simpler Meal for the DishItem
                            items(viewState.results.map { it.toMeal() }) { meal ->
                                DishItem(meal = meal, navigateToRecipe = navigateToRecipe)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper function to adapt our detailed search result to the simpler item composable
private fun com.example.myrecipeapp.data.model.MealDetail.toMeal(): Meal {
    return Meal(
        idMeal = this.idMeal,
        strMeal = this.strMeal,
        strMealThumb = this.strMealThumb
    )
}