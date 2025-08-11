package com.kottland.resto101.data.model

data class Meal(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String,
    val ingredients: List<String>,
    val allergens: List<String> = emptyList(),
    val nutritionInfo: NutritionInfo? = null,
    val preparationTime: Int, // in minutes
    val isVegetarian: Boolean = false,
    val isVegan: Boolean = false,
    val isGlutenFree: Boolean = false,
    val isSpicy: Boolean = false,
    val rating: Float = 0f,
    val reviewCount: Int = 0,
    val isPopular: Boolean = false,
    val isFeatured: Boolean = false
)

data class NutritionInfo(
    val calories: Int,
    val protein: Float, // in grams
    val carbs: Float, // in grams
    val fat: Float, // in grams
    val fiber: Float? = null, // in grams
    val sugar: Float? = null // in grams
)