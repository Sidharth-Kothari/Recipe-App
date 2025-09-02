package com.example.myrecipeapp.ui.category_list

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myrecipeapp.data.model.Category

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewState: CategoryViewModel.RecipeState,
    navigateToDetail: (Category) -> Unit,
    onSearchClicked: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe App") },
                actions = {
                    // This is the search icon button
                    IconButton(onClick = onSearchClicked) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { paddingValues -> // Scaffold provides padding that we must apply
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply the padding here
        ) {
            when {
                viewState.loading -> {
                    CircularProgressIndicator(modifier.align(Alignment.Center))
                }

                viewState.error != null -> {
                    Text("Error Occurred")
                }

                else -> {
                    CategoryScreen(categories = viewState.list, navigateToDetail)
                }
            }
        }
    }
}

@Composable
fun CategoryScreen(categories: List<Category>, navigateToDetail: (Category) -> Unit) {

    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {

        items(categories) {
            category ->
            CategoryItem(category = category,navigateToDetail)
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    navigateToDetail: (Category) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable{ navigateToDetail(category)},
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = category.strCategoryThumb,
            contentDescription = "Image of ${category.strCategory}",
            modifier = Modifier.size(120.dp),
        )

        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}