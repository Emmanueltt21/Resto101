package com.kottland.resto101.ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
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
import com.kottland.resto101.ui.components.MealListCard
import com.kottland.resto101.ui.components.SearchTextField
import com.kottland.resto101.ui.theme.Dimens
import com.kottland.resto101.ui.theme.Resto101Theme

enum class SortOption(val displayName: String) {
    NAME("Name"),
    PRICE_LOW_TO_HIGH("Price: Low to High"),
    PRICE_HIGH_TO_LOW("Price: High to Low"),
    RATING("Rating"),
    PREPARATION_TIME("Preparation Time")
}

enum class FilterOption(val displayName: String) {
    ALL("All"),
    VEGETARIAN("Vegetarian"),
    VEGAN("Vegan"),
    GLUTEN_FREE("Gluten Free"),
    SPICY("Spicy")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    categoryId: String? = null,
    onNavigateBack: () -> Unit,
    onNavigateToMealDetail: (String) -> Unit,
    onAddToCart: (Meal) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedSortOption by remember { mutableStateOf(SortOption.NAME) }
    var selectedFilterOption by remember { mutableStateOf(FilterOption.ALL) }
    var showSortDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    
    val categories = StaticDataProvider.categories
    val selectedCategory = categoryId?.let { id -> categories.find { it.id == id } }
    
    // Filter meals based on category, search, and filters
    val filteredMeals = remember(categoryId, searchQuery, selectedFilterOption) {
        var meals = if (categoryId != null) {
            StaticDataProvider.meals.filter { it.categoryId == categoryId }
        } else {
            StaticDataProvider.meals
        }
        
        // Apply search filter
        if (searchQuery.isNotBlank()) {
            meals = meals.filter { meal ->
                meal.name.contains(searchQuery, ignoreCase = true) ||
                meal.description.contains(searchQuery, ignoreCase = true) ||
                meal.ingredients.any { it.contains(searchQuery, ignoreCase = true) }
            }
        }
        
        // Apply dietary filters
        meals = when (selectedFilterOption) {
            FilterOption.ALL -> meals
            FilterOption.VEGETARIAN -> meals.filter { it.isVegetarian }
            FilterOption.VEGAN -> meals.filter { it.isVegan }
            FilterOption.GLUTEN_FREE -> meals.filter { it.isGlutenFree }
            FilterOption.SPICY -> meals.filter { it.isSpicy }
        }
        
        meals
    }
    
    // Sort meals
    val sortedMeals = remember(filteredMeals, selectedSortOption) {
        when (selectedSortOption) {
            SortOption.NAME -> filteredMeals.sortedBy { it.name }
            SortOption.PRICE_LOW_TO_HIGH -> filteredMeals.sortedBy { it.price }
            SortOption.PRICE_HIGH_TO_LOW -> filteredMeals.sortedByDescending { it.price }
            SortOption.RATING -> filteredMeals.sortedByDescending { it.rating }
            SortOption.PREPARATION_TIME -> filteredMeals.sortedBy { it.preparationTime }
        }
    }
    
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = selectedCategory?.name ?: "Menu",
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
            },
            actions = {
                IconButton(onClick = { showFilterDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Filter"
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
                    placeholder = "Search meals...",
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            }
            
            // Filter and Sort Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.ScreenPadding),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.SpaceSmall)
                ) {
                    // Filter Chip
                    FilterChip(
                        onClick = { showFilterDialog = true },
                        label = { Text(selectedFilterOption.displayName) },
                        selected = selectedFilterOption != FilterOption.ALL,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Sort Chip
                    FilterChip(
                        onClick = { showSortDialog = true },
                        label = { Text("Sort: ${selectedSortOption.displayName}") },
                        selected = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            }
            
            // Results Count
            item {
                Text(
                    text = "${sortedMeals.size} meals found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                )
                
                Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
            }
            
            // Meals List
            if (sortedMeals.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.SpaceExtraLarge),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🔍",
                            style = MaterialTheme.typography.displayLarge
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                        
                        Text(
                            text = "No meals found",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(Dimens.SpaceSmall))
                        
                        Text(
                            text = "Try adjusting your search or filters",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(sortedMeals) { meal ->
                    MealListCard(
                        meal = meal,
                        onMealClick = { onNavigateToMealDetail(meal.id) },
                        onAddToCart = { onAddToCart(meal) },
                        modifier = Modifier.padding(horizontal = Dimens.ScreenPadding)
                    )
                    
                    Spacer(modifier = Modifier.height(Dimens.SpaceMedium))
                }
            }
        }
    }
    
    // Sort Dialog
    if (showSortDialog) {
        AlertDialog(
            onDismissRequest = { showSortDialog = false },
            title = { Text("Sort by") },
            text = {
                Column {
                    SortOption.values().forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedSortOption == option,
                                onClick = { selectedSortOption = option }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option.displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showSortDialog = false }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSortDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Filter Dialog
    if (showFilterDialog) {
        AlertDialog(
            onDismissRequest = { showFilterDialog = false },
            title = { Text("Filter by") },
            text = {
                Column {
                    FilterOption.values().forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedFilterOption == option,
                                onClick = { selectedFilterOption = option }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(option.displayName)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showFilterDialog = false }
                ) {
                    Text("Apply")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showFilterDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuScreenPreview() {
    Resto101Theme {
        MenuScreen(
            categoryId = StaticDataProvider.categories.first().id,
            onNavigateBack = { },
            onNavigateToMealDetail = { },
            onAddToCart = { }
        )
    }
}