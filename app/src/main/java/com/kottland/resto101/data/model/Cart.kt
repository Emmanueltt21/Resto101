package com.kottland.resto101.data.model

data class CartItem(
    val id: String,
    val meal: Meal,
    val quantity: Int,
    val specialInstructions: String? = null
) {
    val totalPrice: Double
        get() = meal.price * quantity
}

data class Cart(
    val items: List<CartItem> = emptyList(),
    val deliveryFee: Double = 2.99,
    val serviceFee: Double = 1.50,
    val tax: Double = 0.0
) {
    val subtotal: Double
        get() = items.sumOf { it.totalPrice }
    
    val totalTax: Double
        get() = subtotal * 0.08 // 8% tax
    
    val total: Double
        get() = subtotal + deliveryFee + serviceFee + totalTax
    
    val itemCount: Int
        get() = items.sumOf { it.quantity }
}