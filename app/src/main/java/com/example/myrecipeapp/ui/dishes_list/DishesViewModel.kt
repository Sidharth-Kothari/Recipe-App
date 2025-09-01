package com.example.myrecipeapp.ui.dishes_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.model.Meal
import com.example.myrecipeapp.data.remote.RetrofitInstance.api
import kotlinx.coroutines.launch


class DishesViewModel: ViewModel() {

    private val _mealState = mutableStateOf(MealState())

    val mealState: State<MealState> = _mealState

    fun fetchMeals(categoryName: String) {

        viewModelScope.launch {
            // Set loading state to true before starting the network call
            _mealState.value = _mealState.value.copy(
                loading = true,
                error = null
            )
            try {
                // Call the new API function
                val response = api.getMealsByCategory(categoryName)
                _mealState.value = _mealState.value.copy(
                    loading = false,
                    list = response.meals, // <-- Use response.meals
                    error = null
                )

            } catch (e: Exception) {
                _mealState.value = _mealState.value.copy(
                    loading = false,
                    error = "Error fetching Meals: ${e.message}"
                )
            }
        }

    }

    data class MealState(
        val loading: Boolean = false,
        val list: List<Meal> = emptyList(),
        val error: String? = null,
    )
}