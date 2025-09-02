package com.example.myrecipeapp.ui.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.model.MealDetail
import com.example.myrecipeapp.data.remote.RetrofitInstance.api
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState

    fun onQueryChange(newQuery: String) {
        // When the user types, update the query and reset the search state
        _searchState.value = _searchState.value.copy(
            query = newQuery,
            searchPerformed = false // <-- Reset so old messages disappear
        )
    }

    fun performSearch() {
        val query = _searchState.value.query
        if (query.isBlank()) {
            _searchState.value = _searchState.value.copy(results = emptyList())
            return
        }

        viewModelScope.launch {
            // Mark that a search is being performed
            _searchState.value = _searchState.value.copy(
                loading = true,
                error = null,
                searchPerformed = true // <-- Mark search as performed
            )
            try {
                val response = api.searchMealsByName(query)
                _searchState.value = _searchState.value.copy(
                    loading = false,
                    results = response.meals ?: emptyList()
                )
            } catch (e: Exception) {
                _searchState.value = _searchState.value.copy(
                    loading = false,
                    error = "Error performing search: ${e.message}"
                )
            }
        }
    }

    data class SearchState(
        val query: String = "",
        val results: List<MealDetail> = emptyList(),
        val loading: Boolean = false,
        val error: String? = null,
        val searchPerformed: Boolean = false // <-- New flag
    )
}