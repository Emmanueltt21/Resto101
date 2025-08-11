package com.kottland.resto101.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kottland.resto101.data.mock.StaticDataProvider
import com.kottland.resto101.data.model.Category
import com.kottland.resto101.data.model.Meal
import com.kottland.resto101.ui.components.CategoryCard
import com.kottland.resto101.ui.components.MealCard
import com.kottland.resto101.ui.components.SearchTextField
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMealDetail: (String) -> Unit,
    onNavigateToCategory: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onAddToCart: (Meal) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    
    val categories = StaticDataProvider.categories
    val featuredMeals = StaticDataProvider.meals.filter { it.isFeatured }
    val popularMeals = StaticDataProvider.meals.filter { it.isPopular }
    
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Good Morning! 👋",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "What would you like to eat?",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = Dimens.ScreenPadding)
        ) {
            item {
                // Search Bar
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                SearchTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Search for food...",
                    onSearch = { onNavigateToSearch() },
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            }
            
            // Categories Section
            item {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimens.ScreenPadding),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
                ) {
                    items(categories) { category ->
                        CategoryCard(
                            category = category,
                            onCategoryClick = { onNavigateToCategory(category.id) }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            }
            
            // Featured Meals Section
            if (featuredMeals.isNotEmpty()) {
                item {
                    Text(
                        text = "Featured Today",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                    
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = Dimens.ScreenPadding),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceMedium)
                    ) {
                        items(featuredMeals) { meal ->
                            MealCard(
                                meal = meal,
                                onMealClick = { onNavigateToMealDetail(meal.id) },
                                onAddToCart = { onAddToCart(meal) },
                                modifier = Modifier.width(280.dp)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
                }
            }
            
            // Popular Meals Section
            if (popularMeals.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.ScreenPadding),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Popular Dishes",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        TextButton(
                            onClick = { onNavigateToSearch() }
                        ) {
                            Text("See All")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                }
                
                items(popularMeals.take(5)) { meal ->
                    MealCard(
                        meal = meal,
                        onMealClick = { onNavigateToMealDetail(meal.id) },
                        onAddToCart = { onAddToCart(meal) },
                        modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                }
            }
            
            // Special Offers Section (Placeholder)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.SpaceLarge),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🎉",
                            style = MaterialTheme.typography.displayMedium
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        Text(
                            text = "Special Offer!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                        
                        Text(
                            text = "Get 20% off on your first order",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        Button(
                            onClick = { onNavigateToSearch() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Order Now")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceLarge))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Resto101Theme {
        HomeScreen(
            onNavigateToMealDetail = { },
            onNavigateToCategory = { },
            onNavigateToSearch = { },
            onAddToCart = { }
        )
    }
}