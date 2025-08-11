package com.kottland.resto101.domain.repository

import com.kottland.resto101.data.model.Category
import com.kottland.resto101.data.model.Meal
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun getCategories(): Flow<List<Category>>
    fun getMeals(): Flow<List<Meal>>
    fun getMealsByCategory(categoryId: String): Flow<List<Meal>>
    fun getFeaturedMeals(): Flow<List<Meal>>
    fun getPopularMeals(): Flow<List<Meal>>
    fun getMealById(mealId: String): Flow<Meal?>
    fun searchMeals(query: String): Flow<List<Meal>>
}