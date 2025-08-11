package com.kottland.resto101.navigation

sealed class Destinations(val route: String) {
    // Authentication
    object Splash : Destinations("splash")
    object Onboarding : Destinations("onboarding")
    object Login : Destinations("login")
    object SignUp : Destinations("signup")
    object ForgotPassword : Destinations("forgot_password")
    
    // Main App
    object Home : Destinations("home")
    object Menu : Destinations("menu/{categoryId}") {
        fun createRoute(categoryId: String) = "menu/$categoryId"
    }
    object MealDetail : Destinations("meal_detail/{mealId}") {
        fun createRoute(mealId: String) = "meal_detail/$mealId"
    }
    object Cart : Destinations("cart")
    object Checkout : Destinations("checkout")
    object OrderConfirmation : Destinations("order_confirmation/{orderId}") {
        fun createRoute(orderId: String) = "order_confirmation/$orderId"
    }
    object OrderHistory : Destinations("order_history")
    object Profile : Destinations("profile")
    object Search : Destinations("search")
    
    // Bottom Navigation Items
    sealed class BottomNav(route: String, val title: String, val icon: String) : Destinations(route) {
        object Home : BottomNav("home", "Home", "home")
        object Menu : BottomNav("categories", "Menu", "restaurant_menu")
        object Cart : BottomNav("cart", "Cart", "shopping_cart")
        object Orders : BottomNav("order_history", "Orders", "receipt_long")
        object Profile : BottomNav("profile", "Profile", "person")
    }
    
    companion object {
        val bottomNavItems = listOf(
            BottomNav.Home,
            BottomNav.Menu,
            BottomNav.Cart,
            BottomNav.Orders,
            BottomNav.Profile
        )
    }
}