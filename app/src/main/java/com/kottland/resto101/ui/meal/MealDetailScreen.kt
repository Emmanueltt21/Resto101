package com.kottland.resto101.ui.meal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kottland.resto101.data.mock.StaticDataProvider
import com.kottland.resto101.data.model.Meal
import com.kottland.resto101.ui.components.PrimaryButton
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    mealId: String,
    onNavigateBack: () -> Unit,
    onAddToCart: (Meal, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val meal = StaticDataProvider.meals.find { it.id == mealId }
    var quantity by remember { mutableStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }
    
    if (meal == null) {
        // Handle meal not found
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Meal not found",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            TextButton(onClick = onNavigateBack) {
                Text("Go Back")
            }
        }
        return
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Image Section with Overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            AsyncImage(
                model = meal.imageUrl,
                contentDescription = meal.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.ScreenPadding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onNavigateBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                    )
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Dimens.ScreenPadding)
        ) {
            item {
                // Meal Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = meal.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${meal.rating} (${meal.reviewCount} reviews)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(Dimens.SpaceMedium))
                            Text(
                                text = "⏱️ ${meal.preparationTime} min",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    
                    Text(
                        text = "$${String.format("%.2f", meal.price)}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            }
            
            // Dietary Tags
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
                ) {
                    if (meal.isVegetarian) {
                        item {
                            AssistChip(
                                onClick = { },
                                label = { Text("🌱 Vegetarian") }
                            )
                        }
                    }
                    if (meal.isVegan) {
                        item {
                            AssistChip(
                                onClick = { },
                                label = { Text("🌿 Vegan") }
                            )
                        }
                    }
                    if (meal.isGlutenFree) {
                        item {
                            AssistChip(
                                onClick = { },
                                label = { Text("🌾 Gluten Free") }
                            )
                        }
                    }
                    if (meal.isSpicy) {
                        item {
                            AssistChip(
                                onClick = { },
                                label = { Text("🌶️ Spicy") }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            }
            
            // Description
            item {
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                Text(
                    text = meal.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            }
            
            // Ingredients
            item {
                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
                ) {
                    items(meal.ingredients) { ingredient ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = ingredient,
                                modifier = Modifier.padding(
                                    horizontal = Dimens.SpaceMedium,
                                    vertical = Dimens.SpaceSmall
                                ),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            }
            
            // Nutrition Info
            if (meal.nutritionInfo != null) {
                item {
                    Text(
                        text = "Nutrition Information",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(Dimens.SpaceMedium)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                NutritionItem("Calories", "${meal.nutritionInfo.calories} kcal")
                                NutritionItem("Protein", "${meal.nutritionInfo.protein}g")
                            }
                            
                            Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                NutritionItem("Carbs", "${meal.nutritionInfo.carbs}g")
                                NutritionItem("Fat", "${meal.nutritionInfo.fat}g")
                            }
                            
                            if ((meal.nutritionInfo.fiber ?: 0f) > 0 || (meal.nutritionInfo.sugar ?: 0f) > 0) {
                                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    if ((meal.nutritionInfo.fiber ?: 0f) > 0) {
                                        NutritionItem("Fiber", "${meal.nutritionInfo.fiber}g")
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                    if ((meal.nutritionInfo.sugar ?: 0f) > 0) {
                                        NutritionItem("Sugar", "${meal.nutritionInfo.sugar}g")
                                    } else {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                }
            }
            
            // Allergens
            if (meal.allergens.isNotEmpty()) {
                item {
                    Text(
                        text = "Allergens",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(Dimens.SpaceMedium)
                        ) {
                            Text(
                                text = "⚠️ Contains:",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            
                            Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                            
                            Text(
                                text = meal.allergens.joinToString(", "),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceExtraLarge))
                }
            }
        }
        
        // Bottom Section - Quantity and Add to Cart
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quantity",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            enabled = quantity > 1
                        ) {
                            Text(
                                text = "-",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = Dimens.SpaceMedium)
                        )
                        
                        IconButton(
                            onClick = { quantity++ }
                        ) {
                            Text(
                                text = "+",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                PrimaryButton(
                    text = "Add to Cart - $${String.format("%.2f", meal.price * quantity)}",
                    onClick = { onAddToCart(meal, quantity) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun NutritionItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MealDetailScreenPreview() {
    Resto101Theme {
        MealDetailScreen(
            mealId = StaticDataProvider.meals.first().id,
            onNavigateBack = { },
            onAddToCart = { _, _ -> }
        )
    }
}