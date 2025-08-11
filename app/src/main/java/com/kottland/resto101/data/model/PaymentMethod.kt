package com.kottland.resto101.data.model

data class PaymentMethod(
    val id: String,
    val type: PaymentType,
    val name: String,
    val details: String = ""
)

enum class PaymentType {
    CREDIT_CARD,
    DEBIT_CARD,
    PAYPAL,
    APPLE_PAY,
    GOOGLE_PAY,
    CASH_ON_DELIVERY,
    CASH
}