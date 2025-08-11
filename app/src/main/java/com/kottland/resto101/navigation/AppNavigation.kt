package com.kottland.resto101.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.kottland.resto101.ui.splash.SplashScreen
import com.kottland.resto101.ui.auth.LoginScreen
import com.kottland.resto101.ui.auth.SignUpScreen
import com.kottland.resto101.ui.auth.ForgotPasswordScreen
import com.kottland.resto101.ui.home.HomeScreen
import com.kottland.resto101.ui.menu.MenuScreen
import com.kottland.resto101.ui.meal.MealDetailScreen
import com.kottland.resto101.ui.cart.CartScreen
import com.kottland.resto101.ui.checkout.CheckoutScreen
import com.kottland.resto101.ui.order.OrderConfirmationScreen
import com.kottland.resto101.ui.order.OrderHistoryScreen
import com.kottland.resto101.ui.profile.ProfileScreen
import com.kottland.resto101.data.mock.StaticDataProvider

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = Destinations.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Splash Screen
        composable(Destinations.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Destinations.Login.route) {
                        popUpTo(Destinations.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Authentication Screens
        composable(Destinations.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Destinations.SignUp.route)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(Destinations.ForgotPassword.route)
                },
                onLoginSuccess = {
                    navController.navigate(Destinations.Home.route) {
                        popUpTo(Destinations.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Destinations.SignUp.route) {
            SignUpScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onSignUpSuccess = {
                    navController.navigate(Destinations.Home.route) {
                        popUpTo(Destinations.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Destinations.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBackToLogin = {
                    navController.popBackStack()
                },
                onResetSent = {
                    // Handle reset sent state
                }
            )
        }
        
        // Main App Screens
        composable(Destinations.Home.route) {
            HomeScreen(
                onNavigateToMealDetail = { mealId ->
                    navController.navigate(Destinations.MealDetail.createRoute(mealId))
                },
                onNavigateToCategory = { categoryId ->
                    navController.navigate(Destinations.Menu.createRoute(categoryId))
                },
                onNavigateToSearch = {
                    // Handle navigate to search
                },
                onAddToCart = { meal ->
                    // Handle add to cart
                }
            )
        }
        
        composable("categories") {
            MenuScreen(
                categoryId = null,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToMealDetail = { mealId ->
                    navController.navigate(Destinations.MealDetail.createRoute(mealId))
                },
                onAddToCart = { meal ->
                    // Handle add to cart
                }
            )
        }
        
        composable(
            route = Destinations.Menu.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType; nullable = true })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")
            MenuScreen(
                categoryId = categoryId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToMealDetail = { mealId ->
                    navController.navigate(Destinations.MealDetail.createRoute(mealId))
                },
                onAddToCart = { meal ->
                    // Handle add to cart
                }
            )
        }
        
        composable(
            route = Destinations.MealDetail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            MealDetailScreen(
                mealId = mealId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAddToCart = { meal, quantity ->
                    // Handle add to cart
                    navController.navigate(Destinations.Cart.route)
                }
            )
        }
        
        composable(Destinations.Cart.route) {
            CartScreen(
                cartItems = emptyList(), // TODO: Get from cart state
                onUpdateQuantity = { mealId, quantity ->
                    // Handle update quantity
                },
                onRemoveItem = { mealId ->
                    // Handle remove item
                },
                onClearCart = {
                    // Handle clear cart
                },
                onProceedToCheckout = {
                    navController.navigate(Destinations.Checkout.route)
                },
                onContinueShopping = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Destinations.Checkout.route) {
            CheckoutScreen(
                cartItems = emptyList(), // TODO: Get from cart state
                onNavigateBack = {
                    navController.popBackStack()
                },
                onPlaceOrder = { checkoutData ->
                    // Handle place order and navigate to confirmation
                    navController.navigate(Destinations.OrderConfirmation.createRoute("order_123")) {
                        popUpTo(Destinations.Home.route)
                    }
                }
            )
        }
        
        composable(
            route = Destinations.OrderConfirmation.route,
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId") ?: ""
            // TODO: Get actual order from orderId
            val sampleOrder = StaticDataProvider.orderHistory.firstOrNull() ?: return@composable
            OrderConfirmationScreen(
                order = sampleOrder,
                onNavigateToHome = {
                    navController.navigate(Destinations.Home.route) {
                        popUpTo(Destinations.Home.route) { inclusive = true }
                    }
                },
                onTrackOrder = {
                    // Handle track order
                }
            )
        }
        
        composable(Destinations.OrderHistory.route) {
            OrderHistoryScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOrderClick = { order ->
                    // Handle view order details
                },
                onReorderClick = { order ->
                    // Handle reorder
                }
            )
        }
        
        composable(Destinations.Profile.route) {
            ProfileScreen(
                onNavigateToOrderHistory = {
                    navController.navigate(Destinations.OrderHistory.route)
                },
                onNavigateToSettings = {
                    // Handle navigate to settings
                },
                onLogout = {
                    navController.navigate(Destinations.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}