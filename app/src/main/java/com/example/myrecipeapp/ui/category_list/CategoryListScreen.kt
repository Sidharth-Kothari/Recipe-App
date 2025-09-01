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

@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    viewState: CategoryViewModel.RecipeState,
    navigateToDetail: (Category) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {

        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text("Error Occurred")
                Log.d("loading failed","${viewState.error}")
            }

            else -> {
//                Display Categories
                CategoryScreen(categories = viewState.list,navigateToDetail)
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