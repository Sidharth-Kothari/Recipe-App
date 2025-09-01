package com.example.myrecipeapp.data.model

data class Meal(
    val strMeal: String,
    val strMealThumb: String,
    val idMeal: String
)

data class MealsResponse(
    val meals: List<Meal>
)