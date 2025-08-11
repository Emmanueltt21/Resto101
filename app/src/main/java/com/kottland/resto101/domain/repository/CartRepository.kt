package com.kottland.resto101.domain.repository

import com.kottland.resto101.data.model.Cart
import com.kottland.resto101.data.model.CartItem
import com.kottland.resto101.data.model.Meal
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCart(): Flow<Cart>
    suspend fun addToCart(meal: Meal, quantity: Int = 1, specialInstructions: String? = null)
    suspend fun updateCartItem(cartItemId: String, quantity: Int)
    suspend fun removeFromCart(cartItemId: String)
    suspend fun clearCart()
    fun getCartItemCount(): Flow<Int>
}