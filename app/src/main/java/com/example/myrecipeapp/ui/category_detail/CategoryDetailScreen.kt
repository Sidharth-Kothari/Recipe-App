package com.example.myrecipeapp.ui.category_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myrecipeapp.data.model.Category

@Composable
fun CategoryDetailScreen(category: Category,onShowDishesClicked: (String) -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = category.strCategory, textAlign = TextAlign.Center)

        AsyncImage(
            model = category.strCategoryThumb,
            contentDescription = "Image of ${category.strCategory}",
            // 3. A size Modifier is added to AsyncImage so it has space on the screen.
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f)
        )

        Text(
            text = category.strCategoryDescription,
            textAlign = TextAlign.Justify,
        )

        Button(onClick = { onShowDishesClicked(category.strCategory) }) {
            Text("Show Dishes")
        }
    }
}