package com.kottland.resto101.data.model

import java.util.Date

data class Order(
    val id: String,
    val items: List<CartItem>,
    val subtotal: Double,
    val deliveryFee: Double,
    val tax: Double,
    val total: Double,
    val status: OrderStatus,
    val orderDate: Date,
    val estimatedDeliveryTime: String,
    val deliveryAddress: String, // Simplified to string for now
    val paymentMethod: PaymentMethod,
    val customerName: String,
    val customerPhone: String
)

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}