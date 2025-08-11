package com.kottland.resto101

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kottland.resto101.navigation.AppNavigation
import com.kottland.resto101.navigation.Destinations
import com.kottland.resto101.ui.theme.Resto101Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Resto101Theme {
                RestaurantApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Define which screens should show bottom navigation
    val bottomNavScreens = setOf(
        Destinations.Home.route,
        "categories", // Menu overview
        Destinations.Cart.route,
        Destinations.OrderHistory.route,
        Destinations.Profile.route
    )
    
    val shouldShowBottomBar = currentDestination?.route in bottomNavScreens
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar {
                    Destinations.bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = getIconForRoute(screen.icon),
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            startDestination = Destinations.Splash.route
        )
    }
}

fun getIconForRoute(iconName: String): ImageVector {
    return when (iconName) {
        "home" -> Icons.Default.Home
        "restaurant_menu" -> Icons.Default.Menu
        "shopping_cart" -> Icons.Default.ShoppingCart
        "receipt_long" -> Icons.Filled.DateRange
        "person" -> Icons.Default.Person
        else -> Icons.Default.Home
    }
}