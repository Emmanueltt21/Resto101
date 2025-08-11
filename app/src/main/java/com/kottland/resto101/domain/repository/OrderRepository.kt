package com.kottland.resto101.domain.repository

import com.kottland.resto101.data.model.Order
import com.kottland.resto101.data.model.PaymentMethod
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
    fun getOrderById(orderId: String): Flow<Order?>
    suspend fun placeOrder(order: Order): Result<Order>
    fun getPaymentMethods(): Flow<List<PaymentMethod>>
}