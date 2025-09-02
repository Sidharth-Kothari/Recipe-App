package com.example.myrecipeapp.ui.recipe_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.model.MealDetail
import com.example.myrecipeapp.data.remote.RetrofitInstance.api
import kotlinx.coroutines.launch

class RecipeDetailViewModel : ViewModel() {

    private val _mealDetailState = mutableStateOf(MealDetailState())
    val mealDetailState: State<MealDetailState> = _mealDetailState

    fun fetchMealDetails(mealId: String) {
        viewModelScope.launch {
            // Set initial loading state
            _mealDetailState.value = _mealDetailState.value.copy(loading = true, error = null)
            try {
                // Fetch the data from the API
                val response = api.getMealDetailsById(mealId)

                // The API returns a list, but it will only contain one meal for a specific ID lookup.
                // We take the first item from that list.
                _mealDetailState.value = _mealDetailState.value.copy(
                    loading = false,
                    meal = response.meals.firstOrNull(),
                    error = null
                )
            } catch (e: Exception) {
                // Handle any errors
                _mealDetailState.value = _mealDetailState.value.copy(
                    loading = false,
                    error = "Error fetching details: ${e.message}"
                )
            }
        }
    }

    // Data class to hold the state for the Recipe Detail screen
    data class MealDetailState(
        val loading: Boolean = false,
        val meal: MealDetail? = null, // Holds a single meal detail, nullable
        val error: String? = null
    )
}