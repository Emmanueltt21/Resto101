package com.kottland.resto101.ui.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.data.mock.StaticDataProvider
import com.kottland.resto101.data.model.CartItem
import com.kottland.resto101.data.model.PaymentMethod
import com.kottland.resto101.data.model.PaymentType
import com.kottland.resto101.ui.components.CustomTextField
import com.kottland.resto101.ui.components.PrimaryButton
import com.kottland.resto101.ui.components.SecondaryButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    cartItems: List<CartItem>,
    onNavigateBack: () -> Unit,
    onPlaceOrder: (CheckoutData) -> Unit,
    modifier: Modifier = Modifier
) {
    var deliveryName by remember { mutableStateOf("") }
    var deliveryPhone by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    var deliveryCity by remember { mutableStateOf("") }
    var deliveryZipCode by remember { mutableStateOf("") }
    var specialInstructions by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }
    var cityError by remember { mutableStateOf<String?>(null) }
    var zipCodeError by remember { mutableStateOf<String?>(null) }
    var paymentError by remember { mutableStateOf<String?>(null) }
    
    var isLoading by remember { mutableStateOf(false) }
    
    val focusManager = LocalFocusManager.current
    val paymentMethods = StaticDataProvider.paymentMethods
    
    val subtotal = cartItems.sumOf { it.meal.price * it.quantity }
    val deliveryFee = 2.99
    val tax = subtotal * 0.08
    val total = subtotal + deliveryFee + tax
    
    fun validateForm(): Boolean {
        nameError = if (deliveryName.isBlank()) "Name is required" else null
        phoneError = if (deliveryPhone.isBlank()) "Phone number is required" 
                    else if (deliveryPhone.length < 10) "Invalid phone number" else null
        addressError = if (deliveryAddress.isBlank()) "Address is required" else null
        cityError = if (deliveryCity.isBlank()) "City is required" else null
        zipCodeError = if (deliveryZipCode.isBlank()) "ZIP code is required" else null
        paymentError = if (selectedPaymentMethod == null) "Please select a payment method" else null
        
        return nameError == null && phoneError == null && addressError == null && 
               cityError == null && zipCodeError == null && paymentError == null
    }
    
    fun handlePlaceOrder() {
        if (validateForm()) {
            isLoading = true
            val checkoutData = CheckoutData(
                deliveryName = deliveryName,
                deliveryPhone = deliveryPhone,
                deliveryAddress = deliveryAddress,
                deliveryCity = deliveryCity,
                deliveryZipCode = deliveryZipCode,
                specialInstructions = specialInstructions,
                paymentMethod = selectedPaymentMethod!!,
                cartItems = cartItems,
                subtotal = subtotal,
                deliveryFee = deliveryFee,
                tax = tax,
                total = total
            )
            onPlaceOrder(checkoutData)
        }
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Checkout",
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
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Dimens.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(Dimens.SpaceLarge)
        ) {
            // Delivery Information Section
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
                        
                        // Name Field
                        CustomTextField(
                            value = deliveryName,
                            onValueChange = { 
                                deliveryName = it
                                nameError = null
                            },
                            label = "Full Name",
                            placeholder = "Enter your full name",
                            leadingIcon = Icons.Default.Person,
                            isError = nameError != null,
                            errorMessage = nameError,
                            imeAction = ImeAction.Next,
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        // Phone Field
                        CustomTextField(
                            value = deliveryPhone,
                            onValueChange = { 
                                deliveryPhone = it
                                phoneError = null
                            },
                            label = "Phone Number",
                            placeholder = "Enter your phone number",
                            leadingIcon = Icons.Default.Phone,
                            isError = phoneError != null,
                            errorMessage = phoneError,
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next,
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        // Address Field
                        CustomTextField(
                            value = deliveryAddress,
                            onValueChange = { 
                                deliveryAddress = it
                                addressError = null
                            },
                            label = "Street Address",
                            placeholder = "Enter your street address",
                            leadingIcon = Icons.Default.Home,
                            isError = addressError != null,
                            errorMessage = addressError,
                            imeAction = ImeAction.Next,
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
                        ) {
                            // City Field
                            CustomTextField(
                                value = deliveryCity,
                                onValueChange = { 
                                    deliveryCity = it
                                    cityError = null
                                },
                                label = "City",
                                placeholder = "City",
                                isError = cityError != null,
                                errorMessage = cityError,
                                imeAction = ImeAction.Next,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            
                            // ZIP Code Field
                            CustomTextField(
                                value = deliveryZipCode,
                                onValueChange = { 
                                    deliveryZipCode = it
                                    zipCodeError = null
                                },
                                label = "ZIP Code",
                                placeholder = "ZIP",
                                isError = zipCodeError != null,
                                errorMessage = zipCodeError,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                modifier = Modifier.weight(0.6f)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        // Special Instructions
                        CustomTextField(
                            value = specialInstructions,
                            onValueChange = { specialInstructions = it },
                            label = "Special Instructions (Optional)",
                            placeholder = "Any special delivery instructions...",
                            leadingIcon = Icons.Default.Edit,
                            imeAction = ImeAction.Done,
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            ),
                            maxLines = 3
                        )
                    }
                }
            }
            
            // Payment Method Section
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
                                imageVector = Icons.Default.AccountBox,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                            Text(
                                text = "Payment Method",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        if (paymentError != null) {
                            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                            Text(
                                text = paymentError!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                        
                        paymentMethods.forEach { paymentMethod ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = selectedPaymentMethod == paymentMethod,
                                        onClick = { 
                                            selectedPaymentMethod = paymentMethod
                                            paymentError = null
                                        }
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (selectedPaymentMethod == paymentMethod) {
                                        MaterialTheme.colorScheme.primaryContainer
                                    } else {
                                        MaterialTheme.colorScheme.surfaceVariant
                                    }
                                )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(Dimens.SpaceMedium),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = selectedPaymentMethod == paymentMethod,
                                        onClick = { 
                                            selectedPaymentMethod = paymentMethod
                                            paymentError = null
                                        }
                                    )
                                    
                                    Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
                                    
                                    Icon(
                                        imageVector = when (paymentMethod.type) {
                                            PaymentType.CREDIT_CARD -> Icons.Default.DateRange
                                            PaymentType.DEBIT_CARD -> Icons.Default.DateRange
                                            PaymentType.PAYPAL -> Icons.Default.DateRange
                                            PaymentType.APPLE_PAY -> Icons.Default.DateRange
                                            PaymentType.GOOGLE_PAY -> Icons.Default.DateRange
                                            PaymentType.CASH_ON_DELIVERY -> Icons.Default.DateRange
                                            PaymentType.CASH -> Icons.Default.DateRange
                                        },
                                        contentDescription = null
                                    )
                                    
                                    Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
                                    
                                    Column {
                                        Text(
                                            text = paymentMethod.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Medium
                                        )
                                        if (paymentMethod.details.isNotBlank()) {
                                            Text(
                                                text = paymentMethod.details,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                        }
                    }
                }
            }
            
            // Order Summary Section
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
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(Dimens.SpaceSmall))
                            Text(
                                text = "Order Summary",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                        
                        // Order Items
                        cartItems.forEach { cartItem ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${cartItem.quantity}x ${cartItem.meal.name}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "$${String.format("%.2f", cartItem.meal.price * cartItem.quantity)}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                        }
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        Divider()
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        // Pricing Breakdown
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Subtotal")
                            Text("$${String.format("%.2f", subtotal)}")
                        }
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Delivery Fee")
                            Text("$${String.format("%.2f", deliveryFee)}")
                        }
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tax")
                            Text("$${String.format("%.2f", tax)}")
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
                                text = "$${String.format("%.2f", total)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
        
        // Bottom Action Buttons
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(
                topStart = Dimens.CornerRadiusLarge,
                topEnd = Dimens.CornerRadiusLarge
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(Dimens.ScreenPadding)
            ) {
                PrimaryButton(
                    text = if (isLoading) "Placing Order..." else "Place Order",
                    onClick = { handlePlaceOrder() },
                    isLoading = isLoading,
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                SecondaryButton(
                    text = "Back to Cart",
                    onClick = onNavigateBack,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

data class CheckoutData(
    val deliveryName: String,
    val deliveryPhone: String,
    val deliveryAddress: String,
    val deliveryCity: String,
    val deliveryZipCode: String,
    val specialInstructions: String,
    val paymentMethod: PaymentMethod,
    val cartItems: List<CartItem>,
    val subtotal: Double,
    val deliveryFee: Double,
    val tax: Double,
    val total: Double
)

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    Resto101Theme {
        val sampleCartItems = listOf(
            CartItem(
                id = "cart_item_1",
                meal = StaticDataProvider.meals[0],
                quantity = 2,
                specialInstructions = ""
            ),
            CartItem(
                id = "cart_item_2",
                meal = StaticDataProvider.meals[1],
                quantity = 1,
                specialInstructions = ""
            )
        )
        
        CheckoutScreen(
            cartItems = sampleCartItems,
            onNavigateBack = { },
            onPlaceOrder = { }
        )
    }
}