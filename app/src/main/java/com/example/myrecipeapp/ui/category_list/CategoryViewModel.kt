package com.example.myrecipeapp.ui.category_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.model.Category
import com.example.myrecipeapp.data.remote.RetrofitInstance.api
import kotlinx.coroutines.launch

class CategoryViewModel: ViewModel() {

    private val _categorieState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categorieState


    init {
        fetchCategories()
    }

    private fun fetchCategories() {

        viewModelScope.launch {

            try {

                val response  = api.getCategories()
                _categorieState.value = _categorieState.value.copy(
                    loading = false,
                    list = response.categories,
                    error =  null
                )
            }
            catch (e : Exception) {
                _categorieState.value = _categorieState.value.copy(
                    loading = false,
                    error =  "Error fetching Categories: ${e.message}"
                )
            }
        }
    }


    data class RecipeState(
        val loading : Boolean = true,
        val list : List<Category> = emptyList(),
        val error : String? = null,
    )

}