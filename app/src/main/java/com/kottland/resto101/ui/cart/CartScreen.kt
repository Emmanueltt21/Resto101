package com.kottland.resto101.ui.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kottland.resto101.data.mock.StaticDataProvider
import com.kottland.resto101.data.model.CartItem
import com.kottland.resto101.data.model.Meal
import com.kottland.resto101.ui.components.PrimaryButton
import com.kottland.resto101.ui.components.SecondaryButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onUpdateQuantity: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit,
    onClearCart: () -> Unit,
    onProceedToCheckout: () -> Unit,
    onContinueShopping: () -> Unit,
    modifier: Modifier = Modifier
) {
    val subtotal = cartItems.sumOf { it.meal.price * it.quantity }
    val deliveryFee = if (cartItems.isNotEmpty()) 2.99 else 0.0
    val tax = subtotal * 0.08 // 8% tax
    val total = subtotal + deliveryFee + tax
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Cart (${cartItems.sumOf { it.quantity }})",
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                if (cartItems.isNotEmpty()) {
                    TextButton(
                        onClick = onClearCart
                    ) {
                        Text(
                            text = "Clear All",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        )
        
        if (cartItems.isEmpty()) {
            // Empty Cart State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.ScreenPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                
                Text(
                    text = "Your cart is empty",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                
                Text(
                    text = "Add some delicious meals to get started",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
                
                PrimaryButton(
                    text = "Start Shopping",
                    onClick = onContinueShopping
                )
            }
        } else {
            // Cart Items
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(Dimens.ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
            ) {
                items(cartItems, key = { it.meal.id }) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onUpdateQuantity = { quantity ->
                            onUpdateQuantity(cartItem.meal.id, quantity)
                        },
                        onRemoveItem = {
                            onRemoveItem(cartItem.meal.id)
                        }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                }
            }
            
            // Order Summary
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = Dimens.CornerRadiusLarge,
                    topEnd = Dimens.CornerRadiusLarge
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.ScreenPadding)
                ) {
                    Text(
                        text = "Order Summary",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    // Subtotal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "$${String.format("%.2f", subtotal)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    // Delivery Fee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Delivery Fee",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "$${String.format("%.2f", deliveryFee)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    // Tax
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Tax",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "$${String.format("%.2f", tax)}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    Divider()
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Total",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$${String.format("%.2f", total)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                    
                    // Buttons
                    PrimaryButton(
                        text = "Proceed to Checkout",
                        onClick = onProceedToCheckout,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    SecondaryButton(
                        text = "Continue Shopping",
                        onClick = onContinueShopping,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onUpdateQuantity: (Int) -> Unit,
    onRemoveItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.SpaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Meal Image
            AsyncImage(
                model = cartItem.meal.imageUrl,
                contentDescription = cartItem.meal.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(Dimens.CornerRadiusMedium)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
            
            // Meal Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cartItem.meal.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                
                Text(
                    text = "$${String.format("%.2f", cartItem.meal.price)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                
                if (cartItem.specialInstructions?.isNotBlank() == true) {
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    Text(
                        text = "Note: ${cartItem.specialInstructions ?: ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
            
            // Quantity Controls
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (cartItem.quantity > 1) {
                                onUpdateQuantity(cartItem.quantity - 1)
                            }
                        },
                        enabled = cartItem.quantity > 1
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Create,
                            contentDescription = "Decrease quantity"
                        )
                    }
                    
                    Text(
                        text = cartItem.quantity.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = Dimens.SpaceSmall)
                    )
                    
                    IconButton(
                        onClick = {
                            onUpdateQuantity(cartItem.quantity + 1)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase quantity"
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                
                // Remove Button
                IconButton(
                    onClick = onRemoveItem
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                
                // Item Total
                Text(
                    text = "$${String.format("%.2f", cartItem.meal.price * cartItem.quantity)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    Resto101Theme {
        val sampleCartItems = listOf(
            CartItem(
                id = "cart_item_1",
                meal = StaticDataProvider.meals[0],
                quantity = 2,
                specialInstructions = "Extra spicy please"
            ),
            CartItem(
                id = "cart_item_2",
                meal = StaticDataProvider.meals[1],
                quantity = 1,
                specialInstructions = ""
            )
        )
        
        CartScreen(
            cartItems = sampleCartItems,
            onUpdateQuantity = { _, _ -> },
            onRemoveItem = { },
            onClearCart = { },
            onProceedToCheckout = { },
            onContinueShopping = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyCartScreenPreview() {
    Resto101Theme {
        CartScreen(
            cartItems = emptyList(),
            onUpdateQuantity = { _, _ -> },
            onRemoveItem = { },
            onClearCart = { },
            onProceedToCheckout = { },
            onContinueShopping = { }
        )
    }
}