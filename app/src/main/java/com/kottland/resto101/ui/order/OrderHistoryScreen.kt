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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.data.mock.StaticDataProvider
import com.kottland.resto101.data.model.Order
import com.kottland.resto101.data.model.OrderStatus
import com.kottland.resto101.ui.components.SecondaryButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryScreen(
    onNavigateBack: () -> Unit,
    onOrderClick: (Order) -> Unit,
    onReorderClick: (Order) -> Unit,
    modifier: Modifier = Modifier
) {
    val orders = StaticDataProvider.orderHistory
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Order History",
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
        
        if (orders.isEmpty()) {
            // Empty State
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.ScreenPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "No Orders",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                
                Text(
                    text = "No Orders Yet",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                
                Text(
                    text = "When you place your first order, it will appear here.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                
                SecondaryButton(
                    text = "Start Shopping",
                    onClick = onNavigateBack
                )
            }
        } else {
            // Orders List
            LazyColumn(
                contentPadding = PaddingValues(Dimens.ScreenPadding),
                verticalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
            ) {
                items(orders) { order ->
                    OrderHistoryCard(
                        order = order,
                        onOrderClick = { onOrderClick(order) },
                        onReorderClick = { onReorderClick(order) }
                    )
                }
                
                // Bottom spacing
                item {
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistoryCard(
    order: Order,
    onOrderClick: () -> Unit,
    onReorderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    
    Card(
        onClick = onOrderClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.SpaceLarge)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Order #${order.id}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = "${dateFormatter.format(order.orderDate)} at ${timeFormatter.format(order.orderDate)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                // Status Badge
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
                        text = when (order.status) {
                            OrderStatus.PENDING -> "Pending"
                            OrderStatus.CONFIRMED -> "Confirmed"
                            OrderStatus.PREPARING -> "Preparing"
                            OrderStatus.OUT_FOR_DELIVERY -> "Out for Delivery"
                            OrderStatus.DELIVERED -> "Delivered"
                            OrderStatus.CANCELLED -> "Cancelled"
                        },
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
            
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            
            // Order Items Summary
            Column {
                order.items.take(2).forEach { cartItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${cartItem.quantity}x ${cartItem.meal.name}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "$${String.format("%.2f", cartItem.meal.price * cartItem.quantity)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    if (cartItem != order.items.take(2).last()) {
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                
                if (order.items.size > 2) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "and ${order.items.size - 2} more item${if (order.items.size - 2 > 1) "s" else ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            Divider()
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            
            // Bottom Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total: $${String.format("%.2f", order.total)}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${order.items.size} item${if (order.items.size > 1) "s" else ""}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
                ) {
                    // View Details Button
                    OutlinedButton(
                        onClick = onOrderClick,
                        contentPadding = PaddingValues(
                            horizontal = Dimens.SpaceMedium,
                            vertical = 8.dp
                        )
                    ) {
                        Text(
                            text = "View Details",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                    
                    // Reorder Button (only for delivered orders)
                    if (order.status == OrderStatus.DELIVERED) {
                        Button(
                            onClick = onReorderClick,
                            contentPadding = PaddingValues(
                                horizontal = Dimens.SpaceMedium,
                                vertical = 8.dp
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Reorder",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryScreenPreview() {
    Resto101Theme {
        OrderHistoryScreen(
            onNavigateBack = { },
            onOrderClick = { },
            onReorderClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryCardPreview() {
    Resto101Theme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OrderHistoryCard(
                order = StaticDataProvider.orderHistory[0],
                onOrderClick = { },
                onReorderClick = { }
            )
            
            OrderHistoryCard(
                order = StaticDataProvider.orderHistory[1],
                onOrderClick = { },
                onReorderClick = { }
            )
        }
    }
}