package com.kottland.resto101.ui.order

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.data.mock.StaticDataProvider
import com.kottland.resto101.data.model.CartItem
import com.kottland.resto101.data.model.Order
import com.kottland.resto101.data.model.OrderStatus
import com.kottland.resto101.ui.components.PrimaryButton
import com.kottland.resto101.ui.components.SecondaryButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderConfirmationScreen(
    order: Order,
    onNavigateToHome: () -> Unit,
    onTrackOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
    val estimatedDeliveryTime = Calendar.getInstance().apply {
        add(Calendar.MINUTE, 30)
    }.time
    
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(Dimens.SpaceLarge)
    ) {
        // Success Header
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.SpaceLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Order Confirmed",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    Text(
                        text = "Order Confirmed!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    Text(
                        text = "Thank you for your order. We're preparing your delicious meal!",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
        
        // Order Details
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.SpaceLarge)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                        Text(
                            text = "Order Details",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                    
                    // Order ID
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Order ID:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = order.id,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    // Order Date
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Order Date:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = dateFormatter.format(order.orderDate),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    // Status
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Status:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Surface(
                            color = when (order.status) {
                                OrderStatus.PENDING -> MaterialTheme.colorScheme.secondaryContainer
                                OrderStatus.CONFIRMED -> MaterialTheme.colorScheme.primaryContainer
                                OrderStatus.PREPARING -> MaterialTheme.colorScheme.tertiaryContainer
                                OrderStatus.OUT_FOR_DELIVERY -> MaterialTheme.colorScheme.primaryContainer
                                OrderStatus.DELIVERED -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                OrderStatus.CANCELLED -> MaterialTheme.colorScheme.errorContainer
                            },
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimens.CornerRadiusSmall)
                        ) {
                            Text(
                                text = order.status.name.replace("_", " "),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = when (order.status) {
                                    OrderStatus.PENDING -> MaterialTheme.colorScheme.onSecondaryContainer
                                    OrderStatus.CONFIRMED -> MaterialTheme.colorScheme.onPrimaryContainer
                                    OrderStatus.PREPARING -> MaterialTheme.colorScheme.onTertiaryContainer
                                    OrderStatus.OUT_FOR_DELIVERY -> MaterialTheme.colorScheme.onPrimaryContainer
                                    OrderStatus.DELIVERED -> Color(0xFF2E7D32)
                                    OrderStatus.CANCELLED -> MaterialTheme.colorScheme.onErrorContainer
                                },
                                modifier = Modifier.padding(
                                    horizontal = Dimens.SpaceSmall,
                                    vertical = 4.dp
                                )
                            )
                        }
                    }
                }
            }
        }
        
        // Delivery Information
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.SpaceLarge)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                        Text(
                            text = "Delivery Information",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                    
                    // Estimated Delivery Time
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                        Column {
                            Text(
                                text = "Estimated Delivery Time",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = order.estimatedDeliveryTime,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    // Delivery Address
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                        Column {
                            Text(
                                text = "Delivery Address",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = order.deliveryAddress,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
        
        // Order Items
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.SpaceLarge)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                        Text(
                            text = "Order Items",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                    
                    order.items.forEach { cartItem ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = cartItem.meal.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Quantity: ${cartItem.quantity}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                if (!cartItem.specialInstructions.isNullOrBlank()) {
                                    Text(
                                        text = "Note: ${cartItem.specialInstructions}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Text(
                                text = "$${String.format("%.2f", cartItem.meal.price * cartItem.quantity)}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        
                        if (cartItem != order.items.last()) {
                            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                            Divider()
                            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        }
                    }
                }
            }
        }
        
        // Order Summary
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(Dimens.SpaceLarge)
                ) {
                    Text(
                        text = "Order Summary",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal")
                        Text("$${String.format("%.2f", order.subtotal)}")
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Delivery Fee")
                        Text("$${String.format("%.2f", order.deliveryFee)}")
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tax")
                        Text("$${String.format("%.2f", order.tax)}")
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    Divider()
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
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
                            text = "$${String.format("%.2f", order.total)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        
        // Action Buttons
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
            ) {
                PrimaryButton(
                    text = "Track Order",
                    onClick = onTrackOrder,
                    modifier = Modifier.fillMaxWidth()
                )
                
                SecondaryButton(
                    text = "Back to Home",
                    onClick = onNavigateToHome,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        
        // Bottom Spacing
        item {
            Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderConfirmationScreenPreview() {
    Resto101Theme {
        val sampleOrder = Order(
            id = "ORD-2024-001",
            items = listOf(
                CartItem(
                    id = "1",
                    meal = StaticDataProvider.meals[0],
                    quantity = 2,
                    specialInstructions = "Extra spicy"
                ),
                CartItem(
                    id = "2",
                    meal = StaticDataProvider.meals[1],
                    quantity = 1,
                    specialInstructions = ""
                )
            ),
            subtotal = 25.98,
            deliveryFee = 2.99,
            tax = 2.32,
            total = 31.29,
            status = OrderStatus.CONFIRMED,
            orderDate = Date(),
            estimatedDeliveryTime = "30-45 minutes",
            deliveryAddress = "123 Main Street, Anytown, AT 12345",
            paymentMethod = StaticDataProvider.paymentMethods[0],
            customerName = "John Doe",
            customerPhone = "+1 (555) 123-4567"
        )
        
        OrderConfirmationScreen(
            order = sampleOrder,
            onNavigateToHome = { },
            onTrackOrder = { }
        )
    }
}