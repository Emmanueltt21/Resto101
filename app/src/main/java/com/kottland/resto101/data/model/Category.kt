package com.kottland.resto101.data.model

data class Category(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isPopular: Boolean = false
)